import { FC } from 'react'
import { encodeTitle, jsonDateToString } from '../../utils/string-utils'
import MarkdownRenderer from './MarkdownRenderer'
import { Link } from 'react-router'
import { useAuthContext } from '../../store/auth-context'
import { PostDto } from '../../../codegen'

const SinglePost: FC<{ post: PostDto }> = ({ post }) => {
    const { token } = useAuthContext()

    const postUrl = '/post/' + post.id + '/' + encodeTitle(post.title)
    const postDate = jsonDateToString(post.date)

    return (
        <article className="post">
            <h1 className="post-title">
                <Link to={postUrl}>{post.title}</Link>
            </h1>
            {token && (
                <div className="text-muted text-right">
                    <Link to={`/admin/post/${post.id}`}>edit</Link>
                </div>
            )}
            <div className="post-text">
                <MarkdownRenderer>{post.text}</MarkdownRenderer>
            </div>
            <div className="text-muted">
                <div className="post-date">{postDate}</div>
            </div>
        </article>
    )
}

export default SinglePost
