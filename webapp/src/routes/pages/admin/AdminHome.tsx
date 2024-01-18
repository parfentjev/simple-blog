import { FC } from 'react'
import { Link } from 'react-router-dom'

const AdminHome: FC = () => {
    return (
        <ul>
            <li>
                <Link to="/admin/post">New post</Link>
            </li>
        </ul>
    )
}

export default AdminHome
