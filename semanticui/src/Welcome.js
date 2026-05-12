import React from 'react';
import { Button, message } from 'antd';
const Welcome = () => {
  const [messageApi, contextHolder] = message.useMessage();
  const info = () => {
    messageApi.info('Hello, Welcome you are login ');
  };
  return (
    <>
      {contextHolder}
      <Button type="primary" onClick={info}>
        show
      </Button>
    </>
  );
};
export default Welcome;
 