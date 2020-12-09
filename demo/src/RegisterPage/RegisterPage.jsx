import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import { userActions } from '../_actions';

class RegisterPage extends React.Component {
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
            submitted: false,
            pwdIsQulified: true,
            phoneIsQulified: true,
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const { name, value } = event.target;
       
        switch (name) {
            case 'password':
                const pwdForm = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9].*?[0-9]).{6,}$/;
                const pwdMatch = pwdForm.test(value);
                this.setState({ pwdIsQulified: pwdMatch});
                break;
            case 'phone':
                const phoneForm = /^[1][3,4,5,7,8][0-9]{9}$/;
                const phoneMatch = phoneForm.test(value);
                this.setState({ phoneIsQulified: phoneMatch})
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

    handleSelect(event) {
        const { name } = event.target;
        const { user } = this.state;
        const index = event.target.selectedIndex;
        
        this.setState({
            user : {
                ...user,
                [name] : index
            }
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

        this.setState( { pwdIsQulified: pwdMatch, phoneIsQulified: phoneMatch , submitted: true});
      
        if (user.name && user.id && user.username && phoneMatch && pwdMatch) {
            this.props.register(user);
        }

    }

    render() {
        const { registering  } = this.props;
        const { user, submitted, pwdIsQulified, phoneIsQulified } = this.state;
        return (
            <div style={{width:'300px', alignSelf:'center'}}>
                <h2>注册页面</h2>
                <form name="form" onSubmit={this.handleSubmit}>
                    <div className={'form-group' + (submitted && !user.name ? ' has-error' : '')}>
                        <label htmlFor="name">用户姓名</label>
                        <input type="text" className="form-control" name="name" value={user.name} onChange={this.handleChange} />
                        {submitted && !user.name &&
                            <div className="help-block">姓名不能为空</div>
                        }
                    </div>
                    <div className={'form-group' + (submitted && !user.username ? ' has-error' : '')}>
                        <label htmlFor="username">用户名</label>
                        <input type="text" className="form-control" name="username" value={user.username} onChange={this.handleChange} />
                        {submitted && !user.username &&
                            <div className="help-block">用户名不能为空</div>
                        }
                    </div>

                    <div className={'form-group' + (submitted && !user.idType ? ' has-error' : '')}>
                        <label htmlFor="idType">请选择证件类型</label>
                        <select className="form-control" name="idType" onChange={this.handleSelect}>
                            <option>身份证</option>
                            <option>护照</option>
                            <option>其他</option>
                        </select>
                       
                    </div>

                    <div className={'form-group' + (submitted && !user.id ? ' has-error' : '')}>
                        <label htmlFor="id">证件号码</label>
                        <input type="text" className="form-control" name="id" value={user.id} onChange={this.handleChange} />
                        {submitted && !user.id &&
                            <div className="help-block">证件号码不能为空</div>
                        }
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
                        <input type="password" className="form-control" name="password" value={user.password} onChange={this.handleChange} />
                        {submitted && !pwdIsQulified && 
                            <div className="help-block">密码至少包含大写字母，小写字母，2个数字，且不少于6位</div>
                        }
                    </div>

                   <div className='form-group'>
                        <label htmlFor="profile">用户简介</label>
                        <input type="text" className="form-control" name="profile" value={user.profile} onChange={this.handleChange} />
                    </div>
                    
                    <div className="form-group">
                        <button className="btn btn-primary">注册</button>
                        {registering && 
                            <img src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
                        }
                        <Link to="/login" className="btn btn-link">取消</Link>
                    </div>
                </form>
            </div>
        );
    }
}

function mapState(state) {
    const { registering } = state.registration;
    return { registering };
}

const actionCreators = {
    register: userActions.register
}

const connectedRegisterPage = connect(mapState, actionCreators)(RegisterPage);
export { connectedRegisterPage as RegisterPage };