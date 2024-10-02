import { FC } from 'react'
import { PostPreviewDto } from '../../api/codegen'
import { Link } from 'react-router-dom'

const EditorPostsList: FC<{ posts: PostPreviewDto[] }> = ({ posts }) => {
    return (
        <ul>
            <>
                {posts.map((post) => (
                    <li key={post.id}>
                        <Link to={`/admin/post/${post.id}`}>{post.title}</Link>
                        {!post.visible && ' DRAFT'}
                    </li>
                ))}
            </>
        </ul>
    )
}

export default EditorPostsList
