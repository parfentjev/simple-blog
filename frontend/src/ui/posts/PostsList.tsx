import { FC } from 'react'
import PostPreview from './PostPreview'
import { PostPreviewDto } from '../../api/codegen'

interface PostsListProps {
    posts: PostPreviewDto[]
}

const PostsList: FC<PostsListProps> = (props) => {
    return (
        <>
            {props.posts.map((post) => (
                <PostPreview key={post.id} post={post} />
            ))}
        </>
    )
}

export default PostsList
