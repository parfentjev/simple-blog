import { FC } from 'react'
import { encodeTitle, jsonDateToString } from '../../utils/string-utils'
import MarkdownRenderer from './MarkdownRenderer'
import { Link } from 'react-router'
import { PostPreviewDto } from '../../../codegen'

const PostPreview: FC<{ post: PostPreviewDto }> = ({ post }) => {
    const postUrl = '/post/' + post.id + '/' + encodeTitle(post.title)
    const postDate = jsonDateToString(post.date)

    return (
        <article className="post">
            <h1 className="post-title">
                <Link to={postUrl}>{post.title}</Link>
            </h1>
            <div className="post-text">
                <MarkdownRenderer>{post.summary}</MarkdownRenderer>
            </div>
            <div>
                <div className="post-date text-muted">{postDate}</div>
                <div className="post-read-more">
                    <Link to={postUrl}>Read more...</Link>
                </div>
            </div>
        </article>
    )
}

export default PostPreview
