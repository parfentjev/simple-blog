import { FC } from 'react'
import { PostPreviewDto } from '../../../../codegen'
import { Link } from 'react-router'

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
