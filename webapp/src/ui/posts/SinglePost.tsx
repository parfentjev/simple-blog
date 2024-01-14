import { FC } from 'react'
import { PostDto } from '../../api/models/post'
import { encodeTitle, jsonDateToString } from '../../utils/string-utils'
import MarkdownRenderer from './MarkdownRenderer'
import { Link } from 'react-router-dom'

const SinglePost: FC<{ post: PostDto }> = ({ post }) => {
    const postUrl = '/post/' + post.id + '/' + encodeTitle(post.title)
    const postDate = jsonDateToString(post.date)

    return (
        <article className="post">
            <h1 className="post-title">
                <Link to={postUrl}>{post.title}</Link>
            </h1>
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
