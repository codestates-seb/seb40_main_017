import { ClientBuyForm } from './ClientBuyform';
import { PayForm } from './PayForm';

export const MultiStepBuyForm = ({ index, nextButton, userData, itemData, count, price, setIsLoading }) => {
  switch (index) {
    case 1:
      return (
        <ClientBuyForm nextButton={nextButton} userData={userData} itemData={itemData} count={count} price={price} setIsLoading={setIsLoading} />
      );
    case 2:
      return <PayForm price={price} />;
    default:
  }
};
