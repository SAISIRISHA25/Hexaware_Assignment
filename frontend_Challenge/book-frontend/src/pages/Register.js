import { Button, Card, Form, Input, Select, message, Typography } from "antd";
import { Link, useNavigate } from "react-router-dom";
import API from "../api/api";

const { Title, Text } = Typography;

function Register() {
  const navigate = useNavigate();

  const registerUser = async (values) => {
    try {
      console.log("REGISTER VALUES:", values);

      await API.post("/auth/register", values);

      message.success("Registration successful. Please login.");
      navigate("/login");
    } catch (error) {
      message.error(error.response?.data?.message || "Registration failed");
    }
  };

  return (
    <div className="auth-page">
      <Card className="auth-card">
        <Title level={2}>Create Account</Title>
        <Text type="secondary">Register to manage your book collection</Text>

        <Form layout="vertical" onFinish={registerUser} className="auth-form">
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

          <Form.Item
            label="Role"
            name="role"
            rules={[{ required: true, message: "Role is required" }]}
          >
            <Select
              size="large"
              placeholder="Select role"
              options={[
                { value: "ADMIN", label: "Admin" },
                { value: "USER", label: "User" },
              ]}
            />
          </Form.Item>

          <Button type="primary" htmlType="submit" size="large" block>
            Register
          </Button>
        </Form>

        <p className="switch-text">
          Already have an account? <Link to="/login">Login</Link>
        </p>
      </Card>
    </div>
  );
}

export default Register;