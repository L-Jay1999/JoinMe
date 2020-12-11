import React from 'react';
import { Table } from 'antd';


class OrderPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            orders: [{
                createDate: '',
                description: '',
                modifyDate: '',
                number: 0,
                orderId: 0,
                orderName: '',
                orderState: '',
                orderType: '',
                picture: '',
                userId: 0,
            }
            ]
        }
    }

    componentDidMount(){
        fetch('/order/',{ method: 'GET'})
            .then(res=>res.json())
            .then(value=>{
                console.log(value);
                const { data } = value;
                console.log(data);
            })
    }

    render() {
        return (
            <div>
                <text>hello</text>
            </div>
        )
    }
}

export default OrderPage;