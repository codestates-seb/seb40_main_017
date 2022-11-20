import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { FiX } from 'react-icons/fi';

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
  width: 1100px;
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
  width: 1100px;
  height: 700px;
  background: var(--white);
  display: grid;
  grid-template-columns: 1fr 3.5fr;
  grid-template-rows: 4fr 1fr 1fr 1fr 1fr;
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

function SellerPostPage() {
  const [form, setForm] = useState({ title: '', stock: '', price: '', category: '' });
  const [img, setImg] = useState([]);
  const [content, setContent] = useState([]);
  const [previewImg, setPreviewImg] = useState([]);
  const [previewContent, setPreviewContent] = useState([]);

  const handleOnchangeForm = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  useEffect(() => {
    setForm({ ...form, mainimage: img, contentimage: content });
  }, [img, content]);
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

      console.log(file);
      reader.readAsDataURL(e.target.files[0]);

      img.push(file);
    }

    reader.onloadend = () => {
      const previewImgUrl = reader.result;
      if (previewImgUrl) {
        setPreviewImg([...previewImg, previewImgUrl]);
      }
    };
    console.log(img);
    setForm({
      ...form,
      [e.target.name]: img,
    });
  };
  // 상세 이미지 input 이벤트
  const handleContentInput = (e) => {
    const files = e.target.files;
    console.log(files);
    if (content.length >= 3) {
      return alert('3개이상 불가능');
    }
    let fileURLs = [];
    let file;
    setContent([...content, ...files]);

    for (let i = 0; i < files.length; i++) {
      file = files[i];
      let maxSize = 4 * 1024 * 1024;
      let fileSize = file.size;

      if (fileSize > maxSize) {
        alert('첨부파일 사이즈는 4MB 이내로 등록 가능합니다.');
        continue;
      }
      content.push(files[i]);
      console.log(content);
      if (fileSize < maxSize) {
        let reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = () => {
          fileURLs[i] = reader.result;
          setPreviewContent([...previewContent, ...fileURLs]);
        };
      }
    }

    setForm({
      ...form,
      [e.target.name]: content,
    });
  };
  // 썸네일 이미지 미리보기
  const getPreviewMain = () => {
    const deleteImg = (index) => {
      const imgArr = img.filter((el, idx) => idx !== index);
      const imgNameArr = previewImg.filter((el, idx) => idx !== index);
      setImg([...imgArr]);
      setPreviewImg([...imgNameArr]);
      console.log(img);
      setForm({ ...form, mainimage: img });
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
  // 상세 이미지 미리보기
  const getPreviewImg = () => {
    // 미리보기 이미지 삭제
    const deleteImg = (index) => {
      const imgArr = content.filter((el, idx) => idx !== index);
      const imgNameArr = previewContent.filter((el, idx) => idx !== index);
      setContent([...imgArr]);
      setPreviewContent([...imgNameArr]);
      console.log(content);
    };

    if (content === null || content.length === 0) {
      return (
        <PreviewContent>
          <img src="https://k-startup.go.kr/images/homepage/prototype/noimage.gif" alt="dd" />
          <span>등록된 이미지가 없습니다.</span>
        </PreviewContent>
      );
    } else {
      return content.map((el, index) => {
        const { name } = el;
        return (
          <PreviewContent key={index}>
            <img src={previewContent[index]} alt="콘텐츠" />
            <div>{name}</div>
            <FiX onClick={() => deleteImg(index)} color="red" />
          </PreviewContent>
        );
      });
    }
  };
  // 판매 등록 버튼 이벤트
  const handlePostButton = () => {
    setForm({ ...form, mainimage: img, contentimage: content });
    console.log(form);
  };

  return (
    <SellerPostLayout>
      <SellerPostHeader>
        <p className="head">판매 물품</p>
        <p>등록</p>
      </SellerPostHeader>
      <SellerPostContent>
        <ContentHead>이미지</ContentHead>
        <ContentImage>
          <div className="image-box">
            <div className="button-box">
              <span> 메인 이미지</span>
              <label htmlFor="file">업로드</label>
              <input type={'file'} name="mainimage" accept={'image/*'} id="file" onChange={handleThumbnailInput}></input>
            </div>
            <PreviewMain>{getPreviewMain()}</PreviewMain>
          </div>
          <div className="image-box">
            <div className="button-box">
              <span> 상세 이미지</span>
              <label htmlFor="file2">업로드</label>
              <input type={'file'} name="contentimage" accept={'image/*'} id="file2" onChange={handleContentInput} multiple></input>
            </div>
            <PreviewMain>{getPreviewImg()}</PreviewMain>
          </div>
        </ContentImage>
        <ContentHead>제목</ContentHead>
        <ContentInput>
          <input placeholder="제목" name="title" onChange={handleOnchangeForm}></input>
        </ContentInput>
        <ContentHead>판매수량</ContentHead>
        <ContentInput>
          <input placeholder="판매수량" name="stock" type="number" onChange={handleOnchangeForm}></input>개
        </ContentInput>
        <ContentHead>상품가격</ContentHead>
        <ContentInput>
          <input placeholder="상품가격" name="price" type="number" onChange={handleOnchangeForm}></input>원
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
      </SellerPostContent>
      <SellerPostButton onClick={handlePostButton}> 등록하기 </SellerPostButton>
    </SellerPostLayout>
  );
}

export default SellerPostPage;
