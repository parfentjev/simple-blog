import { ChangeEvent, FC, FormEvent, useEffect, useState } from 'react'
import styles from './PostEditor.module.css'
import Button, { ButtonStyle } from '../layout/element/Button'
import NamedContainer from '../layout/NamedContainer'
import PostDto from '@/api/models/PostDto'
import Post from './Post'
import { postPosts, putPosts } from '@/api/api-executor'
import { useAuthContext } from '@/store/auth-context'
import { useRouter } from 'next/router'

const EditPostForm: FC<{ post?: PostDto }> = ({ post }) => {
  const { token } = useAuthContext()
  const { push } = useRouter()
  const [errorMessage, setErrorMessage] = useState<string>()

  const [modifiedPost, setModifiedPost] = useState<PostDto>({
    id: post ? post.id : undefined,
    title: post ? post.title : '',
    summary: post ? post.summary : '',
    text: post ? post.text : '',
    date: post ? post.date : new Date().toJSON(),
    visible: post ? post.visible : false,
    category: post ? post.category : ['Other'],
  })

  useEffect(() => {
    if (!post) {
      return
    }

    setModifiedPost({
      id: post.id,
      title: post.title,
      summary: post.summary,
      text: post.text,
      date: post.date,
      visible: post.visible,
      category: post.category,
    })
  }, [post])

  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setModifiedPost((postDto) => {
      return { ...postDto, title: event.target.value }
    })
  }

  const handleSummaryChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setModifiedPost((postDto) => {
      return { ...postDto, summary: event.target.value }
    })
  }

  const handleTextChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setModifiedPost((postDto) => {
      return { ...postDto, text: event.target.value }
    })
  }

  const handleVisibleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setModifiedPost((postDto) => {
      return { ...postDto, visible: event.target.checked }
    })
  }

  const handleSave = async (event: FormEvent) => {
    event.preventDefault()
    setErrorMessage('')

    if (!token) {
      return
    }

    const result = await (post
      ? putPosts(token, modifiedPost)
      : postPosts(token, modifiedPost))
    if (result.message) {
      setErrorMessage(result.message)
    } else {
      push(`/admin/post/${result.id}`)
    }
  }

  return (
    <form className={styles.form} onSubmit={handleSave}>
      <div>
        <input
          type='text'
          placeholder='title'
          onChange={handleTitleChange}
          value={modifiedPost.title}
        />
      </div>
      <div>
        <textarea
          rows={5}
          placeholder='summary'
          onChange={handleSummaryChange}
          value={modifiedPost.summary}
        />
      </div>
      <div>
        <textarea
          rows={10}
          placeholder='text'
          onChange={handleTextChange}
          value={modifiedPost.text}
        />
      </div>
      {modifiedPost && (
        <NamedContainer>
          <Post post={modifiedPost} />
        </NamedContainer>
      )}
      <NamedContainer
        className={styles.options}
        name='Additional options'
        centered={false}
      >
        <div>
          <input
            type='checkbox'
            id='visible'
            onChange={handleVisibleChange}
            checked={modifiedPost.visible}
          />
          <label htmlFor='visible'>Post is visible</label>
        </div>
      </NamedContainer>
      <NamedContainer centered={true}>
        {errorMessage && <p>{errorMessage}</p>}
        <Button text='Save' type='submit' />{' '}
      </NamedContainer>
    </form>
  )
}

export default EditPostForm