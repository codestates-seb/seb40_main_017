import S3 from 'react-aws-s3';

const s3Config = {
  bucketName: process.env.REACT_APP_BUCKET_NAME,
  region: process.env.REACT_APP_REGION,
  accessKeyId: process.env.REACT_APP_ACCESS,
  secretAccessKey: process.env.REACT_APP_SECRET,
};

const s3Client = new S3(s3Config);

const IMAGE_REGEXP = /^(?:bmp|png|jpg|jpeg|gif|webp|svg)$/i;
const IMAGE_MAX_SIZE = 4 * 1024 * 1024; // 4MB

export const uploadImage = async (file, fileName) => {
  if (fileName === void 0) {
    fileName = file.name;
  }

  const fileExtension = fileName.split(/\./).pop();
  if (!IMAGE_REGEXP.test(fileExtension)) {
    alert('허용 되지 않은 확장자입니다.');
    return null;
  }

  const fileSize = file.size;
  if (fileSize >= IMAGE_MAX_SIZE) {
    alert('이미지는 최대 4MB 까지 업로드 가능합니다.');
    return null;
  }

  return await s3Client
    .uploadFile(file, fileName)
    .then((data) => {
      return data.location;
    })
    .catch((error) => {
      console.log(error);
      return null;
    });
};
