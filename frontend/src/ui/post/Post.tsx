import { FC } from 'react'
import styles from './Post.module.css'
import PostDto from '../../api/models/PostDto'
import { encodeTitle, jsonDateToString } from '../../utils/string-utils'
import MarkdownRenderer from './MarkdownRenderer'
import Link from 'next/link'

const Post: FC<{ post: PostDto }> = ({ post }) => {
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

export default Post
