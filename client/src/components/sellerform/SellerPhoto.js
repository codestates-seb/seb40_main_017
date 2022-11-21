import styled from 'styled-components';
import { useForm } from 'react-hook-form';
import { useEffect, useState } from 'react';
import S3 from 'react-aws-s3';
import { FiX } from 'react-icons/fi';
import { Fade } from 'react-awesome-reveal';

const PhotoBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  position: relative;
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
    top: 5px;
  }
`;

const InputBox = styled.div`
  position: relative;
  width: 25em;
  height: 25em;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  h4 {
    position: absolute;
    top: 0;
    font-size: 20px;
  }
  label {
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
`;

const ErrorBox = styled.div`
  font-size: 14px;
  position: absolute;
  color: red;
  display: flex;
  justify-content: center;
  bottom: 2.5em;
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

const PreviewMain = styled.div`
  width: 100%;
  height: 10em;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 2.5rem;
  img {
    width: 150px;
    height: 150px;
  }
`;

const PreviewContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const SellerPhoto = ({ nextButton, formData, setFormData }) => {
  window.Buffer = window.Buffer || require('buffer').Buffer;
  const [img, setImg] = useState([]);
  const [previewImg, setPreviewImg] = useState([]);
  // const [link, setLink] = useState('');
  let link;

  const {
    register,
    formState: { errors },
    watch,
    reset,
    handleSubmit,
  } = useForm();

  const config = {
    bucketName: process.env.REACT_APP_BUCKET_NAME,
    region: process.env.REACT_APP_REGION,
    accessKeyId: process.env.REACT_APP_ACCESS,
    secretAccessKey: process.env.REACT_APP_SECRET,
  };

  const uploadFile = async (file) => {
    const ReactS3Client = new S3(config);
    // the name of the file uploaded is used to upload it to S3
    await ReactS3Client.uploadFile(file, file.name)
      .then((data) => {
        console.log(data.location);
        // setLink(data.location);
        link = data.location;
        return link;
      })
      .catch((err) => console.error(err));
  };

  const onSubmit = async (data) => {
    const file = watch('mainimage');
    console.log(data);
    console.log(img);
    // if (watch('mainimage')) {
    if (img.length !== 0) {
      console.log('파일 있음');
      console.log(file[0]);
      await uploadFile(file[0]);
      console.log(link);
      setFormData({ ...formData, mainimage: link });
      nextButton();
    }
  };

  const image = watch('mainimage');
  useEffect(() => {
    if (image && image.length > 0) {
      const file = image[0];
      if (img.length >= 1) {
        return alert('메인 이미지는 1개만 등록 가능합니다.');
      }
      let reader = new FileReader();

      if (file) {
        let maxSize = 4 * 1024 * 1024;
        let fileSize = file.size;

        if (fileSize > maxSize) {
          return alert('첨부파일 사이즈는 4MB 이내로 등록 가능합니다.');
        }

        console.log(file);
        reader.readAsDataURL(file);

        img.push(file);
      }

      reader.onloadend = () => {
        const previewImgUrl = reader.result;
        if (previewImgUrl) {
          setPreviewImg([...previewImg, previewImgUrl]);
        }
      };
    }
    console.log(img);
  }, [image]);

  const getPreviewMain = () => {
    const deleteImg = (index) => {
      const imgArr = img.filter((el, idx) => idx !== index);
      const imgNameArr = previewImg.filter((el, idx) => idx !== index);
      setImg([...imgArr]);
      setPreviewImg([...imgNameArr]);
      console.log(img);
      // setForm({ ...form, mainimage: img });
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
            <FiX
              onClick={() => {
                deleteImg(index);
                reset();
              }}
              color="red"
            />
          </PreviewContent>
        );
      });
    }
  };

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <PhotoBox>
          <h2>상품 메인 이미지를 업로드 해주세요</h2>
          <Fade cascade damping={0.4}>
            <InputBox>
              <h4>📷메인 이미지</h4>
              <PreviewMain>{getPreviewMain()}</PreviewMain>
              <label htmlFor="file">업로드</label>
              <input type={'file'} name="mainimage" accept={'image/*'} id="file" {...register('mainimage', { required: true })} />
              <ErrorBox>{errors.mainimage && <p>사진을 업로드 해주세요</p>}</ErrorBox>
            </InputBox>
          </Fade>
          <ButtonBox>
            <input type="submit" value="Next" />
          </ButtonBox>
        </PhotoBox>
      </form>
    </>
  );
};
