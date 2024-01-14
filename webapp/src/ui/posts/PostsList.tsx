import { FC } from 'react'
import { PostPreviewDto } from '../../api/models/post'
import PostPreview from './PostPreview'

const PostsList: FC<{ posts: PostPreviewDto[] }> = ({ posts }) => {
    return (
        <>
            {posts.map((post) => (
                <PostPreview key={post.id} post={post} />
            ))}
        </>
    )
}

export default PostsList
