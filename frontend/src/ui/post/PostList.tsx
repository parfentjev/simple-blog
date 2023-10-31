import { FC } from 'react'
import PostPreview from './PostPreview'
import PostPreviewDto from '../../api/models/PostPreviewDto'

const PostList: FC<{ posts: PostPreviewDto[] }> = ({ posts }) => {
  return (
    <>
      {posts.map((post) => (
        <PostPreview key={post.id} post={post} />
      ))}
    </>
  )
}

export default PostList
