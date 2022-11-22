import styled from 'styled-components';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import S3 from 'react-aws-s3';
import { useRef } from 'react';
import axios from 'axios';

const ContentBox = styled.div`
  width: 85%;
  height: 100%;
  /* background: blanchedalmond; */
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 15px;
  font-size: 14px;
  span {
    font-size: 16px;
  }
  h2 {
    text-align: center;
    position: absolute;
    top: -2em;
  }
`;
const ButtonBox = styled.div`
  display: flex;
  width: 100%;
  justify-content: center;
  position: absolute;
  bottom: -5em;
  button,
  input {
    color: var(--white);
    width: 10em;
    height: 3em;
    font-size: 16px;
    background: var(--green);
    border-radius: 15px;
    border: 1px solid var(--white);
    :hover {
      background-color: #9bdd9f;
    }
  }
`;

export const SellerContent = ({ nextButton, formData, setFormData, setIsLoading }) => {
  const editorRef = useRef();
  let dataImgurl;
  const config = {
    bucketName: process.env.REACT_APP_BUCKET_NAME,
    region: process.env.REACT_APP_REGION,
    accessKeyId: process.env.REACT_APP_ACCESS,
    secretAccessKey: process.env.REACT_APP_SECRET,
  };

  // 해당 이미지를 S3 버킷에 저장하고 이미지 링크 전달
  const uploadImage = async (file) => {
    const ReactS3Client = new S3(config);
    await ReactS3Client.uploadFile(file, file.name)
      .then((data) => {
        console.log(data.location); // 이미지 링크 확인
        dataImgurl = data.location; // dataimgurl 에 이미지 링크 할당
        return dataImgurl;
      })
      .catch((err) => console.error(err));
  };

  // 토스트 ui editor 이미지 삽입 함수
  const onUploadImage = async (blob, callback) => {
    console.log('이미지 삽입');
    let blank_pattern = /[\s]/g;
    if (blank_pattern.test(blob.name)) {
      return alert('이미지명에는 공백제거해주세요');
    }
    let maxSize = 4 * 1024 * 1024;
    let fileSize = blob.size;

    if (fileSize > maxSize) {
      return alert('첨부파일 사이즈는 4MB 이내로 등록 가능합니다.');
    }

    await uploadImage(blob); // 버킷에 이미지 저장하고 링크 불러오는 함수
    console.log(dataImgurl); // 이미지 링크 확인
    callback(dataImgurl, 'image'); // 에디터에 해당 이미지 링크 전달
  };

  const onChange = () => {
    const data = editorRef.current.getInstance().getMarkdown(); // 토스트 에디터 작성 내용 콘솔창 보이게 하기
    let contentData = { content: data };
    setFormData({ ...formData, ...contentData });
  };

  const handleOnSubmit = async () => {
    if (window.confirm('등록완료하기')) {
      setIsLoading(true);
      await axios({
        url: `${process.env.REACT_APP_API_URL}/boards`,
        method: 'post',
        data: JSON.stringify(formData),
        headers: { 'Content-Type': 'application/json' },
      })
        .then((res) => console.log(res))
        .catch((err) => console.log(err));

      console.log('데이터 테스트');
      console.log(formData);
      setIsLoading(false);
      nextButton();
    }
  };

  return (
    <>
      <ContentBox>
        <h2>상품 상세 내용 작성</h2>
        <Editor
          height="40em"
          minHeight="400px"
          onChange={onChange}
          ref={editorRef}
          previewStyle="vertical"
          initialEditType="markdown"
          initialValue="상품 상세 내용을 작성 해주세요👨‍🌾"
          hooks={{
            addImageBlobHook: onUploadImage,
          }}
          toolbarItems={[['table', 'image', 'link'], ['heading', 'bold', 'italic', 'strike'], ['hr', 'quote'], ['ul', 'ol', 'task'], ['scrollSync']]}
        />
        <ButtonBox>
          <button onClick={handleOnSubmit}>Submit</button>
        </ButtonBox>
      </ContentBox>
    </>
  );
};
