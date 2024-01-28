import { FC } from 'react'
import PostPreview from './PostPreview'
import { PostPreviewDto } from '../../api/codegen'

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
