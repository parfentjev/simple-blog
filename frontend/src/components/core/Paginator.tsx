import { FC } from 'react'
import { Link } from 'react-router'

interface PaginatorProps {
    page: number
    totalPages: number
    urlPath: string
}

const Paginator: FC<PaginatorProps> = (props) => {
    const showNext = props.page !== props.totalPages
    const showBack = props.page !== 1

    const scrollTop = () => window.scrollTo({ top: 0, behavior: 'smooth' })

    return (
        <div className="text-center paginator">
            <div>
                {showBack && (
                    <Link
                        to={props.urlPath + (props.page - 1)}
                        onClick={scrollTop}
                    >
                        newer
                    </Link>
                )}{' '}
                {showNext && (
                    <Link
                        to={props.urlPath + (props.page + 1)}
                        onClick={scrollTop}
                    >
                        older
                    </Link>
                )}
            </div>
            <div>
                page {props.page} of {props.totalPages}
            </div>
        </div>
    )
}

export default Paginator
