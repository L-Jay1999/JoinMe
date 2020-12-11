import React from 'react';
import { Link } from 'react-router-dom';

class LoginPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: {
                username: '',
                password: ''
            },
            submitted: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        const { name, value } = e.target;
        const { user } = this.state;
        this.setState({
            user: {
                ...user,
                [name]: value
            }
        });
    }

    handleSubmit(e) {
        e.preventDefault();

        this.setState({ submitted: true });
        const user = this.state.user;
        let formData = new FormData();
        formData.append("username", user.username);
        formData.append("password", user.password);
        if (user.username && user.password) {
            fetch('/login',
                {
                    method: "POST",
                    body: formData
                })
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    const { code } = data;
                    if (code === 10000) {
                        window.location.href = './home';
                        console.log(window.location.href);
                    }
                    else {
                        alert('登录失败，请确认用户名和密码！')
                    }
                })
                .catch(() => console.log("err"))
        }
    }

    render() {

        const { user, submitted } = this.state;
        const { username, password } = user;

        return (
            <div style={{ width: '300px', height: '300px', alignSelf: 'center' }}>
                <h2>Login</h2>
                <form name="form" onSubmit={this.handleSubmit}>
                    <div className={'form-group' + (submitted && !username ? ' has-error' : '')}>
                        <label htmlFor="username">Username</label>
                        <input type="text" className="form-control" name="username" value={username} onChange={this.handleChange} />
                        {submitted && !username &&
                            <div className="help-block">Username is required</div>
                        }
                    </div>
                    <div className={'form-group' + (submitted && !password ? ' has-error' : '')}>
                        <label htmlFor="password">Password</label>
                        <input type="password" className="form-control" name="password" value={password} onChange={this.handleChange} />
                        {submitted && !password &&
                            <div className="help-block">Password is required</div>
                        }
                    </div>
                    <div className="form-group">
                        <button className="btn btn-primary">Login</button>
                        <Link to="/register" className="btn btn-link">注册</Link>
                    </div>
                </form>
            </div>
        );
    }
}

export default LoginPage;