import { ClientBuyForm } from './ClientBuyform';
import { PayForm } from './PayForm';

export const MultiStepBuyForm = ({ index, nextButton, userData, itemData, count, price }) => {
  switch (index) {
    case 1:
      return <ClientBuyForm nextButton={nextButton} userData={userData} itemData={itemData} count={count} price={price} />;
    case 2:
      return <PayForm price={price} />;
    default:
  }
};
