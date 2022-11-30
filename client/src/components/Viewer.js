import { Viewer } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';

//판매 상세 페이지 에서 본문을 나타내는 뷰어
const ItemViewer = ({ content }) => {
  const markdownData = content;

  return (
    <>
      <Viewer initialValue={markdownData} height="500px" />
    </>
  );
};
export default ItemViewer;
