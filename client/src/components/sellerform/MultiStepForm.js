import { SellerComplete } from './SellerComplete';
import { SellerContent } from './SellerContent';
import { SellerPhoto } from './SellerPhoto';
import { SellerPrice } from './SellerPrice';
import { SellerTitle } from './SellerTitle';

export const MultiStepForm = ({ index, nextButton, formData, setFormData, setIsLoading }) => {
  switch (index) {
    case 1:
      return <SellerTitle nextButton={nextButton} formData={formData} setFormData={setFormData} />;
    case 2:
      return <SellerPrice nextButton={nextButton} formData={formData} setFormData={setFormData} />;
    case 3:
      return <SellerPhoto nextButton={nextButton} formData={formData} setFormData={setFormData} />;
    case 4:
      return <SellerContent nextButton={nextButton} formData={formData} setFormData={setFormData} setIsLoading={setIsLoading} />;
    case 5:
      return <SellerComplete />;
    default:
  }
};
