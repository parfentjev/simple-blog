import { FC } from 'react'
import styles from './Post.module.css'
import PostPreview from '../../api/models/PostPreview'
import Post from '../../api/models/Post'
import { encodeTitle, jsonDateToString } from '../../utils/string-utils'
import MarkdownRenderer from './MarkdownRenderer'
import Link from 'next/link'

export const PostPreviewComponent: FC<{ post: PostPreview }> = ({ post }) => {
  const encodedTitle = encodeTitle(post.title)
  const dateString = jsonDateToString(post.date)

  return (
    <article className={styles.post}>
      <h1 className={styles.title}>
        <Link href={`/post/` + post.id + '/' + encodedTitle}>{post.title}</Link>
      </h1>
      <div className={styles.text}>
        <MarkdownRenderer text={post.summary} />
      </div>
      <div>
        <div className={styles.date}>{dateString}</div>
        <div className={styles.read}>
          <Link href={`/post/` + post.id + '/' + encodedTitle}>
            Read more...
          </Link>
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
        <Link href={`/post/` + post.id + '/' + encodedTitle}>{post.title}</Link>
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
