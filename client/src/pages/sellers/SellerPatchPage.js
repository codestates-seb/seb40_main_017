import { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { FiX } from 'react-icons/fi';
import axios from 'axios';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import S3 from 'react-aws-s3';
import { apiServer } from '../../features/axios';
import { useSelector } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { DotSpinner } from '@uiball/loaders';

const SellerPostLayout = styled.div`
  margin-top: 3.5em;
  margin-bottom: 3.5em;
  background: var(--off-white);
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  form {
    flex-direction: column;
    display: flex;
    justify-content: center;
    align-items: center;
  }
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
  grid-template-rows: 4fr 1fr 1fr 1fr 4fr;
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
  position: relative;
  input {
    width: 300px;
    height: 30px;
  }
  select {
    width: 300px;
    height: 30px;
  }
`;

const ErrorBox = styled.div`
  font-size: 14px;
  position: absolute;
  color: red;
  display: flex;
  justify-content: center;
  left: 30em;
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
  gap: 5em;
  img {
    width: 100px;
    height: 100px;
  }
  .beforeimage {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
`;

const PreviewContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const SellerPostButton = styled.div`
  input {
    width: 200px;
    height: 50px;
    margin-top: 30px;
    margin-bottom: 30px;
    font-size: 18px;
    color: var(--white);
    background: var(--green);
    border: 1px solid transparent;
    border-radius: 30px;
    :hover {
      background: #9bdd9f;
    }
  }
`;

function SellerPatchPage() {
  window.Buffer = window.Buffer || require('buffer').Buffer;
  const editorRef = useRef();
  const sellerInfo = useSelector((state) => state.user.sellerId);
  const location = useLocation();
  const boardInfo = location.state.boardId;
  const navigate = useNavigate();
  const [form, setForm] = useState({ sellerId: sellerInfo, boardId: boardInfo });
  const [img, setImg] = useState([]);
  const [previewImg, setPreviewImg] = useState([]);
  const [itemData, setItemData] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  const {
    register,
    formState: { errors },
    reset,
    handleSubmit,
    setValue,
  } = useForm({ defaultValues: { title: itemData.title, price: itemData.price } });
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
        setItemData((prevState) => {
          return { ...prevState, ...res.data };
        });
        setForm({ ...form, category: res.data.category, price: res.data.price });
        setValue('category', res.data.category);
      })
      .catch((error) => console.log(error));
  };
  const setContent = () => {
    editorRef.current.getInstance().setMarkdown(itemData.content);
  };

  useEffect(() => {
    getItem();
  }, []);

  useEffect(() => {
    setContent();
    reset({ title: itemData.title, price: itemData.price });
  }, [itemData]);

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
        dataImgurl = data.location; // dataimgurl 에 이미지 링크 할당
        return dataImgurl;
      })
      .catch((err) => console.error(err));
  };

  // 토스트 ui editor 이미지 삽입 함수
  const onUploadImage = async (blob, callback) => {
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
    callback(dataImgurl, 'image'); // 에디터에 해당 이미지 링크 전달
  };

  const onChange = () => {
    const data = editorRef.current.getInstance().getMarkdown(); // 토스트 에디터 작성 내용 콘솔창 보이게 하기
    let contentData = { content: data };
    setForm({ ...form, ...contentData });
  };
  // 판매 수정 등록 버튼 이벤트
  const onPatch = async (data) => {
    if (!form.content) {
      return alert('상세내용을 작성해주세요');
    } else if (!form.price) {
      return alert('가격을 수정해주세요');
    }

    setForm((prevState) => {
      return { ...prevState, ...data };
    });

    if (window.confirm('수정확인')) {
      setIsLoading(true);
      await apiServer({
        method: 'patch',
        url: `/boards/${boardInfo}`,
        data: JSON.stringify(form),
        headers: { 'Content-Type': 'application/json' },
      })
        .then(() => {
          alert('수정 완료 되었습니다.');
          navigate(-1);
        })
        .catch(() => {
          alert('수정 실패 하였습니다');
          navigate(-1);
        });
    }
  };

  return (
    <SellerPostLayout>
      {isLoading && <DotSpinner className="animation" size={70} speed={1.75} color="var(--green)" />}
      {!isLoading && (
        <form onSubmit={handleSubmit(onPatch)}>
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
                <PreviewMain>
                  <div className="beforeimage">
                    <img src={itemData.mainImage} alt="이전이미지" />
                    이전이미지
                  </div>
                  {getPreviewMain()}
                </PreviewMain>
              </div>
            </ContentImage>
            <ContentHead>상품명</ContentHead>
            <ContentInput>
              <input
                placeholder="상품명"
                name="title"
                {...register('title', { required: true, maxLength: 20 })}
                onChange={handleOnchangeForm}
              ></input>
              <ErrorBox>
                {errors.title?.type === 'required' && <p>상품명을 적어 주세요</p>}
                {errors.title?.type === 'maxLength' && <p> 20자 이하로 적어 주세요</p>}
              </ErrorBox>
            </ContentInput>
            <ContentHead>상품가격</ContentHead>
            <ContentInput>
              <input
                placeholder="상품가격"
                name="price"
                type="number"
                min={1}
                {...register('price', { required: true })}
                onChange={handleOnchangeForm}
              ></input>
              원<ErrorBox>{errors.price && <p>상품가격을 적어 주세요</p>}</ErrorBox>
            </ContentInput>
            <ContentHead>카테고리</ContentHead>
            <ContentInput>
              <select name="category" {...register('category')} onChange={handleOnchangeForm}>
                <option value={1}>과일</option>
                <option value={2}>채소</option>
                <option value={3}>쌀/잡곡</option>
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
          <SellerPostButton>
            <input type="submit" value="수정하기" />
          </SellerPostButton>
        </form>
      )}
    </SellerPostLayout>
  );
}

export default SellerPatchPage;
