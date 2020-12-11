import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { post } from '../../API';

class RegisterPage extends React.Component {
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
                userType: 'Normal',
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
                this.setState({ pwdIsQulified: pwdMatch });
                break;
            case 'phoneNumber':
                const phoneForm = /^[1][3,4,5,7,8][0-9]{9}$/;
                const phoneMatch = phoneForm.test(value);
                this.setState({ phoneIsQulified: phoneMatch })
                break;
            default:
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
        const { user } = this.state;
        const index = event.target.selectedIndex;

        const type= (
            index === 0 ? 'Identity' : 'Passport'
        );

        this.setState({
            user: {
                ...user,
                cardType: type,
            }
        });
    }

    async handleSubmit(event) {
        event.preventDefault();

        const { user } = this.state;

        const pwdForm = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9].*?[0-9]).{6,}$/;
        const pwdMatch = pwdForm.test(user.password);
        const phoneForm = /^[1][3,4,5,7,8][0-9]{9}$/;
        const phoneMatch = phoneForm.test(user.phoneNumber);

        this.setState({ pwdIsQulified: pwdMatch, phoneIsQulified: phoneMatch, submitted: true });

        if (user.name && user.cardNumber && user.realName && phoneMatch && pwdMatch) {
            fetch('/user/create',
            {
                method:"POST",
                body: JSON.stringify(this.state.user),
                headers:{
                    'content-type': 'application/json'
                }
            })
            .then((res)=>res.json())
            .then((value)=>{
                console.log(value);
                const { code } = value;
                if(code === 10000){
                    window.location.href = '../';
                }
                else{
                    alert('注册失败');
                }
            })
            .catch(()=>{
                console.log("err");
                alert('出现错误！');
            })
        }
    }

    render() {
        const { user, submitted, pwdIsQulified, phoneIsQulified } = this.state;
        return (
            <div style={{ width: '300px', alignSelf: 'center' }}>
                <h2>注册页面</h2>
                <form name="form" onSubmit={this.handleSubmit}>
                    <div className={'form-group' + (submitted && !user.realName ? ' has-error' : '')}>
                        <label htmlFor="realName">用户姓名</label>
                        <input type="text" className="form-control" name="realName" value={user.realName} onChange={this.handleChange} />
                        {submitted && !user.realName &&
                            <div className="help-block">姓名不能为空</div>
                        }
                    </div>
                    <div className={'form-group' + (submitted && !user.name ? ' has-error' : '')}>
                        <label htmlFor="name">用户名</label>
                        <input type="text" className="form-control" name="name" value={user.name} onChange={this.handleChange} />
                        {submitted && !user.name &&
                            <div className="help-block">用户名不能为空</div>
                        }
                    </div>

                    <div className={'form-group' + (submitted && !user.cardType ? ' has-error' : '')}>
                        <label htmlFor="cardType">请选择证件类型</label>
                        <select className="form-control" name="cardType" onChange={this.handleSelect}>
                            <option>身份证</option>
                            <option>护照</option>
                        </select>

                    </div>

                    <div className={'form-group' + (submitted && !user.cardNumber ? ' has-error' : '')}>
                        <label htmlFor="cardNumber">证件号码</label>
                        <input type="text" className="form-control" name="cardNumber" value={user.cardNumber} onChange={this.handleChange} />
                        {submitted && !user.cardNumber &&
                            <div className="help-block">证件号码不能为空</div>
                        }
                    </div>

                    <div className={'form-group' + (submitted && !phoneIsQulified ? ' has-error' : '')}>
                        <label htmlFor="phoneNumber">手机号码</label>
                        <input type="text" className="form-control" name="phoneNumber" value={user.phoneNumber} onChange={this.handleChange} />
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
                        <label htmlFor="introduction">用户简介</label>
                        <input type="text" className="form-control" name="introduction" value={user.introduction} onChange={this.handleChange} />
                    </div>
                    <div className="form-group">
                        <button className="btn btn-primary">注册</button>
                    </div>
                </form>
            </div>
        );
    }
}

export default RegisterPage;