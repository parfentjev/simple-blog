import { FC } from 'react'
import { Link } from 'react-router-dom'
import { useAuthContext } from '../../store/auth-context'
import { toast } from 'react-toastify'

const AdminHome: FC = () => {
    const { token } = useAuthContext()

    const onClickHandler = () => {
        if (!token) {
            return
        }

        navigator.clipboard
            .writeText(token.token)
            .then(() => toast.success('Token is copied.'))
            .catch((_) => toast.error('Failed to copy token.'))
    }

    return (
        <>
            <div className="text-center">
                <input
                    type="text"
                    value={token?.token}
                    onClick={onClickHandler}
                    readOnly
                />
            </div>
            <ul>
                <li>
                    <Link to="/admin/post">New post</Link>
                </li>
                <li>
                    <Link to="/admin/posts">Post list</Link>
                </li>
            </ul>
        </>
    )
}

export default AdminHome
