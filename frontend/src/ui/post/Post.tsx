import { FC, ReactNode } from 'react'
import styles from './Post.module.css'
import PostDto from '../../api/models/PostDto'
import { encodeTitle, jsonDateToString } from '../../utils/string-utils'
import MarkdownRenderer from './MarkdownRenderer'
import Link from 'next/link'
import { useAuthContext } from '@/store/auth-context'

const Post: FC<{ post: PostDto }> = ({ post }) => {
  const { token } = useAuthContext()
  const encodedTitle = encodeTitle(post.title)
  const dateString = jsonDateToString(post.date)

  return (
    <article className={styles.post}>
      <h1 className={styles.title}>
        {(post.id && (
          <Link href={`/post/` + post.id + '/' + encodedTitle}>
            {post.title}
          </Link>
        )) ||
          post.title}
      </h1>
      <div className={styles.text}>
        <MarkdownRenderer text={post.text} />
      </div>
      <div>
        <div className={`text_muted ${styles.date}`}>{dateString}</div>
        <div className={`text_muted ${styles.categories}`}>
          {post.category
            .map<ReactNode>((i) => (
              <Link key={i} href={`/categories/${i}`}>
                {i}
              </Link>
            ))
            .reduce((p1, p2) => [p1, ', ', p2])}
        </div>
      </div>
      <div>
        {token && (
          <div className={`text_muted ${styles.actions}`}>
            <p>
              <Link href={`/admin/post/${post.id}`}>edit</Link>
            </p>
          </div>
        )}
      </div>
    </article>
  )
}

export default Post
