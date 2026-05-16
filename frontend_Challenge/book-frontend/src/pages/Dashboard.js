import { useEffect, useState } from "react";
import {
  Button,
  Card,
  Form,
  Input,
  InputNumber,
  Modal,
  Popconfirm,
  Space,
  Table,
  Tag,
  Typography,
  message,
} from "antd";
import {
  PlusOutlined,
  EditOutlined,
  DeleteOutlined,
  LogoutOutlined,
  BookOutlined,
  SearchOutlined,
} from "@ant-design/icons";
import { useNavigate } from "react-router-dom";
import API from "../api/api";

const { Title, Text } = Typography;

function Dashboard() {
  const [books, setBooks] = useState([]);
  const [filteredBooks, setFilteredBooks] = useState([]);
  const [open, setOpen] = useState(false);
  const [editingBook, setEditingBook] = useState(null);
  const [form] = Form.useForm();

  const navigate = useNavigate();

  const username = localStorage.getItem("username");
  const role = localStorage.getItem("role");
  const isAdmin = role === "ADMIN";

  const fetchBooks = async () => {
    try {
      const response = await API.get("/books");
      setBooks(response.data.data);
      setFilteredBooks(response.data.data);
    } catch (error) {
      message.error(error.response?.data?.message || "Failed to fetch books");
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  const openAddModal = () => {
    setEditingBook(null);
    form.resetFields();
    setOpen(true);
  };

  const openEditModal = (book) => {
    setEditingBook(book);
    form.setFieldsValue(book);
    setOpen(true);
  };

  const saveBook = async (values) => {
    try {
      if (editingBook) {
        await API.put(`/books/${editingBook.isbn}`, values);
        message.success("Book updated successfully");
      } else {
        await API.post("/books", values);
        message.success("Book added successfully");
      }

      setOpen(false);
      form.resetFields();
      fetchBooks();
    } catch (error) {
      message.error(error.response?.data?.message || "Operation failed");
    }
  };

  const deleteBook = async (isbn) => {
    try {
      await API.delete(`/books/${isbn}`);
      message.success("Book deleted successfully");
      fetchBooks();
    } catch (error) {
      message.error(error.response?.data?.message || "Delete failed");
    }
  };

  const searchBooks = (value) => {
    const searchValue = value.toLowerCase();

    const result = books.filter(
      (book) =>
        book.title.toLowerCase().includes(searchValue) ||
        book.author.toLowerCase().includes(searchValue) ||
        book.isbn.toLowerCase().includes(searchValue)
    );

    setFilteredBooks(result);
  };

  const logout = () => {
    localStorage.clear();
    message.success("Logged out successfully");
    navigate("/login");
    window.location.reload();
  };

  const columns = [
    {
      title: "Title",
      dataIndex: "title",
      render: (text) => <b>{text}</b>,
    },
    {
      title: "Author",
      dataIndex: "author",
    },
    {
      title: "ISBN",
      dataIndex: "isbn",
      render: (isbn) => <Tag color="blue">{isbn}</Tag>,
    },
    {
      title: "Publication Year",
      dataIndex: "publicationYear",
      render: (year) => <Tag color="green">{year}</Tag>,
    },
    {
      title: "Actions",
      render: (_, record) =>
        isAdmin ? (
          <Space>
            <Button
              icon={<EditOutlined />}
              onClick={() => openEditModal(record)}
            >
              Edit
            </Button>

            <Popconfirm
              title="Delete Book"
              description="Are you sure you want to delete this book?"
              onConfirm={() => deleteBook(record.isbn)}
              okText="Yes"
              cancelText="No"
            >
              <Button danger icon={<DeleteOutlined />}>
                Delete
              </Button>
            </Popconfirm>
          </Space>
        ) : (
          <Tag color="default">View Only</Tag>
        ),
    },
  ];

  return (
    <div className="dashboard-page">
      <div className="topbar">
        <div>
          <Title level={3} className="top-title">
            <BookOutlined /> Book Management System
          </Title>

          <Text className="welcome-text">
            Welcome, {username} | Role: {role}
          </Text>
        </div>

        <Button danger icon={<LogoutOutlined />} onClick={logout}>
          Logout
        </Button>
      </div>

      <Card className="dashboard-card">
        <div className="toolbar">
          <Input
            size="large"
            prefix={<SearchOutlined />}
            placeholder="Search by title, author, or ISBN"
            onChange={(e) => searchBooks(e.target.value)}
            className="search-box"
          />

          {isAdmin && (
            <Button
              type="primary"
              size="large"
              icon={<PlusOutlined />}
              onClick={openAddModal}
            >
              Add Book
            </Button>
          )}
        </div>

        <Table
          columns={columns}
          dataSource={filteredBooks}
          rowKey="id"
          pagination={{ pageSize: 5 }}
        />
      </Card>

      <Modal
        title={editingBook ? "Update Book" : "Add New Book"}
        open={open}
        onCancel={() => setOpen(false)}
        footer={null}
      >
        <Form layout="vertical" form={form} onFinish={saveBook}>
          <Form.Item
            label="Title"
            name="title"
            rules={[
              { required: true, message: "Title is required" },
              { min: 2, message: "Title must have at least 2 characters" },
            ]}
          >
            <Input placeholder="Enter book title" />
          </Form.Item>

          <Form.Item
            label="Author"
            name="author"
            rules={[
              { required: true, message: "Author is required" },
              { min: 2, message: "Author must have at least 2 characters" },
            ]}
          >
            <Input placeholder="Enter author name" />
          </Form.Item>

          <Form.Item
            label="ISBN"
            name="isbn"
            rules={[
              { required: true, message: "ISBN is required" },
              { min: 3, message: "ISBN must have at least 3 characters" },
            ]}
          >
            <Input placeholder="Enter ISBN" />
          </Form.Item>

          <Form.Item
            label="Publication Year"
            name="publicationYear"
            rules={[{ required: true, message: "Publication year is required" }]}
          >
            <InputNumber
              min={1000}
              max={2026}
              style={{ width: "100%" }}
              placeholder="Enter publication year"
            />
          </Form.Item>

          <Button type="primary" htmlType="submit" block>
            {editingBook ? "Update Book" : "Add Book"}
          </Button>
        </Form>
      </Modal>
    </div>
  );
}

export default Dashboard;