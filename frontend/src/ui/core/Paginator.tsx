import { FC } from 'react'
import { Link } from 'react-router-dom'

interface PaginatorProps {
    page: number
    totalPages: number
}

const Paginator: FC<PaginatorProps> = (props) => {
    const showNext = props.page !== props.totalPages
    const showBack = props.page !== 1

    return (
        <div className="text-center paginator">
            <div>
                {showBack && <Link to={`/posts/${props.page - 1}`}>newer</Link>}{' '}
                {showNext && <Link to={`/posts/${props.page + 1}`}>older</Link>}
            </div>
            <div>
                page {props.page} of {props.totalPages}
            </div>
        </div>
    )
}

export default Paginator
