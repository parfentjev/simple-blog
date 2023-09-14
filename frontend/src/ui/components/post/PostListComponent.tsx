import { FC } from 'react'
import { PostPreviewComponent } from './PostComponent'
import PostPreview from '../../../api/models/PostPreview'

const PostListComponent: FC<{ posts: PostPreview[] }> = ({ posts }) => {
  return (
    <>
      {posts.map((post) => (
        <PostPreviewComponent key={post.id} post={post} />
      ))}
    </>
  )
}

export default PostListComponent
