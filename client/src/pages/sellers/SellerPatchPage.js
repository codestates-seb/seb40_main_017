import { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { FiX } from 'react-icons/fi';
import axios from 'axios';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import S3 from 'react-aws-s3';
import { apiServer } from '../../features/axios';
import { useSelector } from 'react-redux';
import { useLocation } from 'react-router-dom';

const SellerPostLayout = styled.div`
  background: var(--off-white);
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const SellerPostHeader = styled.div`
  width: 34.5em;
  height: 50px;
  margin-bottom: 30px;
  display: flex;
  align-items: center;
  font-size: 30px;
  font-weight: 500;
  gap: 10px;
  border-bottom: 5px solid var(--green);
  .head {
    color: var(--green);
  }
`;

const SellerPostContent = styled.div`
  width: 65em;
  height: 50em;
  background: var(--white);
  display: grid;
  grid-template-columns: 1fr 3.5fr;
  grid-template-rows: 4fr 1fr 1fr 1fr 1fr 4fr;
  .image-box {
    border: none;
  }
`;
const ContentHead = styled.div`
  background: var(--green);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  font-weight: bold;
  color: var(--white);
  border-bottom: 1px solid white;
`;
const ContentInput = styled.div`
  display: flex;
  align-items: center;
  padding: 15px;
  border-bottom: 1px solid white;
  input {
    width: 300px;
    height: 30px;
  }
  select {
    width: 300px;
    height: 30px;
  }
`;

const ContentImage = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-bottom: 1px solid white;
  gap: 20px;
  padding: 15px;
  .image-box {
    min-width: 200px;
    width: 700px;
    height: 150px;
    padding: 15px;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
  }
  .button-box {
    width: 150px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 5px;
    label,
    button {
      display: inline-block;
      text-align: center;
      padding: 0.5em 0.75em;
      color: var(--white);
      font-size: 14px;
      line-height: normal;
      vertical-align: middle;
      background-color: var(--green);
      cursor: pointer;
      border: 1px solid #ebebeb;
      border-bottom-color: #e2e2e2;
      border-radius: 0.25em;
    }
    input {
      position: absolute;
      width: 1px;
      height: 1px;
      padding: 0;
      margin: -1px;
      overflow: hidden;
      clip: rect(0, 0, 0, 0);
      border: 0;
    }
  }
`;

const PreviewMain = styled.div`
  width: 100%;
  height: 150px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* flex-direction: column; */
  gap: 10px;
  img {
    width: 100px;
    height: 100px;
  }
`;

const PreviewContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const SellerPostButton = styled.button`
  width: 200px;
  height: 50px;
  margin-top: 30px;
  font-size: 18px;
  color: var(--white);
  background: var(--green);
  border: 1px solid transparent;
  border-radius: 30px;
  :hover {
    background: #9bdd9f;
  }
`;

function SellerPatchPage() {
  window.Buffer = window.Buffer || require('buffer').Buffer;
  const editorRef = useRef();
  const sellerInfo = useSelector((state) => state.user.sellerId);
  const [form, setForm] = useState({ sellerId: sellerInfo, boardId: boardInfo });
  const [img, setImg] = useState([]);
  const [previewImg, setPreviewImg] = useState([]);
  const [itemData, setItemData] = useState({});
  const location = useLocation();
  const boardInfo = location.state.boardId;

  const handleOnchangeForm = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  // 수정할 페이지 정보 불러오기
  const getItem = async () => {
    await axios
      .get(`${process.env.REACT_APP_API_URL}/boards/${boardInfo}`)
      .then((res) => {
        console.log(res);
        console.log(res.data);
        setItemData((prevState) => {
          return { ...prevState, ...res.data };
        });
        console.log('데이터 넣기');
      })
      .catch((error) => console.log(error));
  };
  const setContent = () => {
    editorRef.current.getInstance().setMarkdown(itemData.content);
    console.log('콘텐츠 넣기');
  };

  useEffect(() => {
    getItem();
    console.log(itemData);
  }, []);

  useEffect(() => {
    setContent();
  }, [itemData]);

  // 메인이미지
  // useEffect(() => {
  //   console.log(mainImage);
  //   setForm((prevState) => ({ ...prevState, mainImage: mainImage }));
  //   // setForm({ ...form, mainImage: mainImage });
  // }, [mainImage]);

  useEffect(() => {
    console.log(form);
    // setForm({ ...form, mainImage: mainImage });
  }, [form]);

  // 썸네일 이미지 input 이벤트
  const handleThumbnailInput = (e) => {
    const file = e.target.files[0];
    if (img.length >= 1) {
      return alert('메인 이미지는 1개만 등록 가능합니다.');
    }
    let reader = new FileReader();

    if (e.target.files[0]) {
      let maxSize = 4 * 1024 * 1024;
      let fileSize = file.size;

      if (fileSize > maxSize) {
        return alert('첨부파일 사이즈는 4MB 이내로 등록 가능합니다.');
      }
      reader.readAsDataURL(e.target.files[0]);

      img.push(file);
      uploadMainImage(file);
    }

    reader.onloadend = () => {
      const previewImgUrl = reader.result;
      if (previewImgUrl) {
        setPreviewImg([...previewImg, previewImgUrl]);
      }
    };
  };

  // 썸네일 이미지 미리보기
  const getPreviewMain = () => {
    const deleteImg = (index) => {
      const imgArr = img.filter((el, idx) => idx !== index);
      const imgNameArr = previewImg.filter((el, idx) => idx !== index);
      setImg([...imgArr]);
      setPreviewImg([...imgNameArr]);
      console.log(img);
    };
    if (img === null || img.length === 0) {
      return (
        <PreviewContent>
          <img src="https://k-startup.go.kr/images/homepage/prototype/noimage.gif" alt="dd" />
          <span>등록된 이미지가 없습니다.</span>
        </PreviewContent>
      );
    } else {
      return img.map((el, index) => {
        const { name } = el;
        return (
          <PreviewContent key={index}>
            <img src={previewImg[index]} alt="콘텐츠" />
            <div>{name}</div>
            <FiX onClick={() => deleteImg(index)} color="red" />
          </PreviewContent>
        );
      });
    }
  };
  let mainImgurl = '';
  let dataImgurl;
  const config = {
    bucketName: process.env.REACT_APP_BUCKET_NAME,
    region: process.env.REACT_APP_REGION,
    accessKeyId: process.env.REACT_APP_ACCESS,
    secretAccessKey: process.env.REACT_APP_SECRET,
  };

  // 메인 이미지를 S3 버킷에 저장하고 이미지 링크 전달
  const uploadMainImage = async (file) => {
    const ReactS3Client = new S3(config);
    await ReactS3Client.uploadFile(file, file.name)
      .then((data) => {
        console.log(data.location); // 이미지 링크 확인
        mainImgurl = data.location; // dataimgurl 에 이미지 링크 할당
        setForm((prevState) => ({ ...prevState, mainImage: mainImgurl }));
        return mainImgurl;
      })
      .catch((err) => console.error(err));
  };
  // 콘텐츠 이미지를 S3 버킷에 저장하고 이미지 링크 전달
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
    setForm({ ...form, ...contentData });
    console.log(form);
  };
  // 판매 수정 등록 버튼 이벤트
  const handlePostButton = async () => {
    // await uploadMainImage(img[0]);
    console.log(form);

    if (window.confirm('확인')) {
      // axios
      //   .patch(`${process.env.REACT_APP_API_URL}/boards/2`, form)
      //   .then((res) => console.log(res))
      //   .catch((err) => console.log(err));
      apiServer({
        method: 'patch',
        url: `/boards/10`,
        data: JSON.stringify(form),
        headers: { 'Content-Type': 'application/json' },
      })
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    }
  };

  return (
    <SellerPostLayout>
      <SellerPostHeader>
        <p className="head">판매 물품</p>
        <p>수정</p>
      </SellerPostHeader>
      <SellerPostContent>
        <ContentHead>이미지</ContentHead>
        <ContentImage>
          <div className="image-box">
            <div className="button-box">
              <span> 메인 이미지</span>
              <label htmlFor="file">업로드</label>
              <input type={'file'} name="mainImage" accept={'image/*'} id="file" onChange={handleThumbnailInput}></input>
            </div>
            <PreviewMain>{getPreviewMain()}</PreviewMain>
          </div>
        </ContentImage>
        <ContentHead>상품명</ContentHead>
        <ContentInput>
          <input placeholder="상품명" name="title" defaultValue={itemData.title} onChange={handleOnchangeForm}></input>
        </ContentInput>
        <ContentHead>판매수량</ContentHead>
        <ContentInput>
          <input placeholder="판매수량" name="stock" defaultValue={itemData.stock} type="number" onChange={handleOnchangeForm}></input>개
        </ContentInput>
        <ContentHead>상품가격</ContentHead>
        <ContentInput>
          <input placeholder="상품가격" name="price" defaultValue={itemData.price} type="number" onChange={handleOnchangeForm}></input>원
        </ContentInput>
        <ContentHead>카테고리</ContentHead>
        <ContentInput>
          <select name="category" onChange={handleOnchangeForm}>
            <option> 카테고리 선택</option>
            <option value={1}>과일</option>
            <option value={2}>채소</option>
            <option value={3}>곡물</option>
            <option value={4}>견과류</option>
          </select>
        </ContentInput>
        <ContentHead>상품 상세</ContentHead>
        <ContentInput>
          {' '}
          {itemData && (
            <Editor
              usageStatistics={false}
              height="20em"
              onChange={onChange}
              ref={editorRef}
              previewStyle="vertical"
              initialEditType="markdown"
              initialValue={'수정 하려는 상품정보를 적어주세요'}
              hooks={{
                addImageBlobHook: onUploadImage,
              }}
              toolbarItems={[
                ['table', 'image', 'link'],
                ['heading', 'bold', 'italic', 'strike'],
                ['hr', 'quote'],
                ['ul', 'ol', 'task'],
                ['scrollSync'],
              ]}
            />
          )}
        </ContentInput>
      </SellerPostContent>
      <SellerPostButton onClick={handlePostButton}> 등록하기 </SellerPostButton>
    </SellerPostLayout>
  );
}

export default SellerPatchPage;
