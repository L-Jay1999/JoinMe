import React from 'react';

class UserPage extends React.Component {

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
            submitted: false,
            phoneIsQulified: true,
            pwdIsQulified: true
        }

        this.GMTToStr = this.GMTToStr.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        const { user } = this.state.user;
        fetch('/user/', { method: 'GET' })
            .then(res => res.json())
            .then(value => {
                const { data } = value;
                this.setState({
                    user: {
                        ...user,
                        ...data,
                        password:'',
                    }
                })
            })
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

    handleSubmit() {
        const { user } = this.state;

        const pwdForm = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9].*?[0-9]).{6,}$/;
        const pwdMatch = pwdForm.test(user.password);
        const phoneForm = /^[1][3,4,5,7,8][0-9]{9}$/;
        const phoneMatch = phoneForm.test(user.phoneNumber);

        this.setState({ pwdIsQulified: pwdMatch, phoneIsQulified: phoneMatch, submitted: true });

        if (phoneMatch && pwdMatch) {
            const ret = fetch('/user/', {
                method: 'POST',
                body: JSON.stringify(this.state.user),
                headers: {
                    'content-type': 'application/json'
                }
            })
                .then(res => {
                    console.log(res.json());
                    return res.json();
                })
                .then(value => {
                    const { code } = value;
                    if (code === 10000) {
                        alert('修改成功！');
                    }
                    else {
                        alert('修改失败！');
                    }
                })
                .catch(err => alert(err));
        }
    }

    GMTToStr(time) {
        const date = new Date(time);
        const Str = date.getFullYear() + '-' +
            (date.getMonth() + 1) + '-' +
            date.getDate() + ' ' +
            date.getHours() + ':' +
            date.getMinutes() + ':' +
            date.getSeconds();
        return Str;
    }

    render() {
        const { user, submitted, pwdIsQulified, phoneIsQulified } = this.state;
        return (
            <div>
                <li htmlFor="registerDate">注册时间：{this.GMTToStr(user.registerDate)}</li>
                <li htmlFor="modifyDate">修改时间：{this.GMTToStr(user.modifyDate)}</li>
                <li htmlFor="userType">用户类型：{user.userType === 'Normal' ? '普通用户' : '系统管理员'}</li>
                <li htmlFor="levelType">用户级别：{user.levelType === 'Normal' ? '一般' : user.levelType === 'Important' ? '重要' : '钻石'}</li>
                <text />
                <form name="form" onSubmit={this.handleSubmit} style={{ alignItems: 'center' }}>
                    <div className='form-group'>
                        <label htmlFor="realName">用户姓名</label>
                        <input type="text" className="form-control" name="realName" value={user.realName} disabled />
                    </div>
                    <div className='form-group'>
                        <label htmlFor="name">用户名</label>
                        <input type="text" className="form-control" name="name" value={user.name} disabled />
                    </div>

                    <div className='form-group'>
                        <label htmlFor="cardType">证件类型</label>
                        <input type="text" className="form-control" name="cardType" value={
                            user.cardType === 'Identity' ? '身份证' : '护照'
                        } disabled />

                    </div>
                    <div className='form-group'>
                        <label htmlFor="cardNumber">证件号码</label>
                        <input type="text" className="form-control" name="cardNumber" value={user.cardNumber} disabled />
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
                        {!user.password || submitted && !pwdIsQulified &&
                            <div className="help-block">密码至少包含大写字母，小写字母，2个数字，且不少于6位</div>
                        }
                    </div>

                    <div className='form-group'>
                        <label htmlFor="introduction">用户简介</label>
                        <input type="text" className="form-control" name="introduction" value={user.introduction} onChange={this.handleChange} />
                    </div>
                </form>
                <div>
                    <button className="btn btn-primary" onClick={this.handleSubmit}>修改</button>
                </div>
            </div>
        )
    }
}

export default UserPage;