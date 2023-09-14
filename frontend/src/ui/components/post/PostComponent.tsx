import { FC } from 'react'
import styles from './Post.module.css'
import { NavLink } from 'react-router-dom'
import PostPreview from '../../../api/models/PostPreview'
import Post from '../../../api/models/Post'
import { encodeTitle, jsonDateToString } from '../../../utils/string-utils'
import MarkdownRenderer from './MarkdownRenderer'

export const PostPreviewComponent: FC<{ post: PostPreview }> = ({ post }) => {
  const encodedTitle = encodeTitle(post.title)
  const dateString = jsonDateToString(post.date)

  return (
    <article className={styles.post}>
      <h1 className={styles.title}>
        <NavLink to={`/post/` + post.id + '/' + encodedTitle}>
          {post.title}
        </NavLink>
      </h1>
      <div className={styles.text}>
        <MarkdownRenderer text={post.summary} />
      </div>
      <div>
        <div className={styles.date}>{dateString}</div>
        <div className={styles.read}>
          <NavLink to={`/post/` + post.id + '/' + encodedTitle}>
            Read more...
          </NavLink>
        </div>
      </div>
    </article>
  )
}

export const PostComponent: FC<{ post: Post }> = ({ post }) => {
  const encodedTitle = encodeTitle(post.title)
  const dateString = jsonDateToString(post.date)

  return (
    <article className={styles.post}>
      <h1 className={styles.title}>
        <NavLink to={`/post/` + post.id + '/' + encodedTitle}>
          {post.title}
        </NavLink>
      </h1>
      <div className={styles.text}>
        <MarkdownRenderer text={post.text} />
      </div>
      <div>
        <div className={styles.date}>{dateString}</div>
      </div>
    </article>
  )
}
