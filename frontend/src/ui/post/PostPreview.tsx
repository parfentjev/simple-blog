import { FC } from 'react'
import styles from './Post.module.css'
import PostPreviewDto from '../../api/models/PostPreviewDto'
import { encodeTitle, jsonDateToString } from '../../utils/string-utils'
import MarkdownRenderer from './MarkdownRenderer'
import Link from 'next/link'

const PostPreview: FC<{ post: PostPreviewDto }> = ({ post }) => {
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

export default PostPreview