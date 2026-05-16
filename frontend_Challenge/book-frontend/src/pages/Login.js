import { Button, Card, Form, Input, message, Typography } from "antd";
import { Link, useNavigate } from "react-router-dom";
import API from "../api/api";

const { Title, Text } = Typography;

function Login() {
  const navigate = useNavigate();

  const loginUser = async (values) => {
    try {
      const response = await API.post("/auth/login", values);

      const token = response.data.data.token;
      const role = response.data.data.role;

      localStorage.setItem("token", token);
      localStorage.setItem("role", role);
      localStorage.setItem("username", values.username);

      message.success("Login successful");
      navigate("/dashboard");
      window.location.reload();
    } catch (error) {
      message.error(error.response?.data?.message || "Invalid login");
    }
  };

  return (
    <div className="auth-page">
      <Card className="auth-card">
        <Title level={2}>Book Management</Title>
        <Text type="secondary">Login to access your secured dashboard</Text>

        <Form layout="vertical" onFinish={loginUser} className="auth-form">
          <Form.Item
            label="Username"
            name="username"
            rules={[{ required: true, message: "Username is required" }]}
          >
            <Input size="large" placeholder="Enter username" />
          </Form.Item>

          <Form.Item
            label="Password"
            name="password"
            rules={[{ required: true, message: "Password is required" }]}
          >
            <Input.Password size="large" placeholder="Enter password" />
          </Form.Item>

          <Button type="primary" htmlType="submit" size="large" block>
            Login
          </Button>
        </Form>

        <p className="switch-text">
          New user? <Link to="/register">Register</Link>
        </p>
      </Card>
    </div>
  );
}

export default Login;