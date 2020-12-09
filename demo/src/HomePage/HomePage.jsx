import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import '!style-loader!css-loader!antd/dist/antd.css';
import '!style-loader!css-loader!./index.css';
import { userActions } from '../_actions';
import { Layout, Menu } from 'antd';
import {
    MenuUnfoldOutlined,
    MenuFoldOutlined,
    UserOutlined,
    VideoCameraOutlined,
    UploadOutlined,
} from '@ant-design/icons';

const { Header, Sider, Content } = Layout;

class HomePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: {
                name: '',
                type: 0,
                username: '',
                idType: 0,
                id: '',
                phone: '',
                profile: '',
                password: ''
            },
            collapsed: false,
            showInfo: true,
            submitted: false,
            phoneIsQulified: true,
            pwdIsQulified: true
        };

        this.toggle = this.toggle.bind(this);
        this.handleInfoClick = this.handleInfoClick.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    toggle() {
        this.setState({
            collapsed: !this.state.collapsed,
        });
    };

    componentDidMount() {
        this.props.getUsers();
        this.setState({
            user: this.props.user
        })
    }

    handleInfoClick(e) {
        this.setState({
            showInfo: true,
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        // this.setState({ submitted: true });
        const { user } = this.state;

        const pwdForm = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9].*?[0-9]).{6,}$/;
        const pwdMatch = pwdForm.test(user.password);
        const phoneForm = /^[1][3,4,5,7,8][0-9]{9}$/;
        const phoneMatch = phoneForm.test(user.phone);

        this.setState({ pwdIsQulified: pwdMatch, phoneIsQulified: phoneMatch, submitted: true });

        if (phoneMatch && pwdMatch) {
            this.props.register(user);
        }
    }

    handleChange(event) {
        const { name, value } = event.target;

        switch (name) {
            case 'password':
                const pwdForm = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9].*?[0-9]).{6,}$/;
                const pwdMatch = pwdForm.test(value);
                this.setState({ pwdIsQulified: pwdMatch });
                break;
            case 'phone':
                const phoneForm = /^[1][3,4,5,7,8][0-9]{9}$/;
                const phoneMatch = phoneForm.test(value);
                this.setState({ phoneIsQulified: phoneMatch })
                break;
        }

        const { user } = this.state;
        this.setState({
            user: {
                ...user,
                [name]: value
            },
        });
    }

    // handleDeleteUser(id) {
    //     return (e) => this.props.deleteUser(id);
    // }

    render() {
        const { registering  } = this.props;
        const { user, showInfo, submitted, pwdIsQulified, phoneIsQulified } = this.state;

        return (
            <Layout>
                <Sider trigger={null} collapsible collapsed={this.state.collapsed}>
                    <div className="logo" ></div>
                    <Menu theme="dark" mode="inline" defaultSelectedKeys={['1']}>
                        <Menu.Item key="1" icon={<UserOutlined />} onClick={this.handleInfoClick}>
                            用户信息
                        </Menu.Item>
                        <Menu.Item key="2" icon={<VideoCameraOutlined />}>
                            nav 2
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
                        {showInfo &&
                            <form name="form" onSubmit={this.handleSubmit}>
                                <div className='form-group'>
                                    <label htmlFor="name">用户姓名</label>
                                    <input type="text" className="form-control" name="username" value={user.name} disabled />
                                </div>
                                <div className='form-group'>
                                    <label htmlFor="username">用户名</label>
                                    <input type="text" className="form-control" name="username" value={user.username} disabled />
                                </div>

                                <div className='form-group'>
                                    <label htmlFor="idType">证件类型</label>
                                    <input type="text" className="form-control" name="idType" value={user.username} disabled />

                                </div>

                                <div className='form-group'>
                                    <label htmlFor="id">证件号码</label>
                                    <input type="text" className="form-control" name="id" value={user.id} disabled />
                                </div>

                                <div className={'form-group' + (submitted && !phoneIsQulified ? ' has-error' : '')}>
                                    <label htmlFor="phone">手机号码</label>
                                    <input type="text" className="form-control" name="phone" value={user.phone} onChange={this.handleChange} />
                                    {submitted && !phoneIsQulified &&
                                        <div className="help-block">手机号码未填写或不正确</div>
                                    }
                                </div>

                                <div className={'form-group' + (submitted && !pwdIsQulified ? ' has-error' : '')}>
                                    <label htmlFor="password">密码</label>
                                    <input type="password" className="form-control" name="password" value={typeof (user.password)} onChange={this.handleChange} />
                                    {submitted && !pwdIsQulified &&
                                        <div className="help-block">密码至少包含大写字母，小写字母，2个数字，且不少于6位</div>
                                    }
                                </div>

                                <div className='form-group'>
                                    <label htmlFor="profile">用户简介</label>
                                    <input type="text" className="form-control" name="profile" value={user.profile} onChange={this.handleChange} />
                                </div>

                                <div className="form-group">
                                    <button className="btn btn-primary">修改</button>
                                    {registering &&
                                        <img src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
                                    }
                                </div>
                            </form>

                        }
                    </Content>
                </Layout>
            </Layout>
        );
    }
}

function mapState(state) {
    const { users, authentication } = state;
    const { user } = authentication;
    const { registering } = state.registration;
    return { user, users, registering };
}

const actionCreators = {
    getUsers: userActions.getAll,
    deleteUser: userActions.delete,
    register: userActions.register
}

const connectedHomePage = connect(mapState, actionCreators)(HomePage);
export { connectedHomePage as HomePage };