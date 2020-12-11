import React from 'react';
import 'antd/dist/antd.css';
import './index.css';
import { Layout, Menu } from 'antd';
import {
    MenuUnfoldOutlined,
    MenuFoldOutlined,
    UserOutlined,
    VideoCameraOutlined,
    UploadOutlined,
} from '@ant-design/icons';
import UserPage from './UserPage';
import OrderPage from './OrderPage';

const { Header, Sider, Content } = Layout;

class HomePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: {
                cardNumber: '',
                cardType: 'Identity',
                introduction: '',
                levelType: 'Normal',
                name: '',
                password: '',
                phoneNumber: '',
                realName: '',
                userId: 0,
                userType: 'Normal',
                registerDate: '',
                modifyDate: '',
            },
            collapsed: false,
            showInfo: true,
            showOrder: false,
            submitted: false,
            phoneIsQulified: true,
            pwdIsQulified: true
        };

        this.toggle = this.toggle.bind(this);
        this.handleClick = this.handleClick.bind(this);
    }

    toggle() {
        this.setState({
            collapsed: !this.state.collapsed,
        });
    };

    handleClick(event) {
        const { key } = event;

        console.log(key);
        switch (key) {
            case 'showInfo':
                this.setState({
                    showInfo: true,
                    showOrder: false,
                });
                console.log("set");
                break;
            case 'showOrder':
                this.setState({
                    showInfo: false,
                    showOrder: true,
                });
                console.log("set");
                break;
            default:
                break;
        }

    }

    render() {

        const { showInfo, showOrder } = this.state;

        return (
            <Layout>
                <Sider trigger={null} collapsible collapsed={this.state.collapsed}>
                    <div className="logo" ></div>
                    <Menu theme="dark" mode="inline" defaultSelectedKeys={['showInfo']} onClick={this.handleClick}>
                        <Menu.Item key="showInfo" icon={<UserOutlined />} >
                            用户信息
                        </Menu.Item>
                        <Menu.Item key="showOrder" icon={<VideoCameraOutlined />} >
                            命令信息
                        </Menu.Item>
                        <Menu.Item key="3" icon={<UploadOutlined />}>
                            nav 3
                        </Menu.Item>
                    </Menu>
                </Sider>
                <Layout className="site-layout">
                    <Header className="site-layout-background" style={{ padding: 0 }}>
                        {React.createElement(this.state.collapsed ? MenuUnfoldOutlined : MenuFoldOutlined, {
                            className: 'trigger',
                            onClick: this.toggle,
                        })}
                    </Header>
                    <Content
                        className="site-layout-background"
                        style={{
                            margin: '24px 16px',
                            padding: 24,
                            minHeight: 280,
                        }}
                    >
                        {showInfo && <UserPage />}
                        {showOrder && <OrderPage />}
                    </Content>
                </Layout>
            </Layout>
        );
    }
}

export default HomePage;