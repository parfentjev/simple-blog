import { ChangeEvent, FC, FormEvent, useEffect, useState } from 'react'
import styles from './PostEditor.module.css'
import Button from '../layout/element/Button'
import Container from '../layout/Container'
import PostDto from '@/api/models/PostDto'
import Post from './Post'
import { getCategories, postPosts, putPosts } from '@/api/api-executor'
import { useAuthContext } from '@/store/auth-context'
import { useRouter } from 'next/router'
import { toast } from 'react-toastify'
import CategoryDto from '@/api/models/CategoryDto'

const EditPostForm: FC<{ post?: PostDto }> = ({ post }) => {
  const { token } = useAuthContext()
  const { push } = useRouter()
  const [possibleCategories, setPossibleCategories] = useState<CategoryDto[]>(
    [],
  )
  const [categoriesValid, setCategoriesValid] = useState(true)

  const [postState, setPostState] = useState<PostDto>({
    id: post ? post.id : undefined,
    title: post ? post.title : '',
    summary: post ? post.summary : '',
    text: post ? post.text : '',
    date: post ? post.date : new Date().toJSON(),
    visible: post ? post.visible : false,
    categories: post ? post.categories : [],
  })

  useEffect(() => {
    if (!post) {
      return
    }

    setPostState({
      id: post.id,
      title: post.title,
      summary: post.summary,
      text: post.text,
      date: post.date,
      visible: post.visible,
      categories: post.categories,
    })
  }, [post])

  useEffect(() => {
    getCategories().then((response) => {
      response.message
        ? toast.error(response.message)
        : setPossibleCategories(response.map((i) => i))
    })
  }, [])

  useEffect(() => {
    let invalidCategories: string[] = []

    postState.categories.forEach((i) => {
      if (
        possibleCategories.findIndex(
          (j) => i.name.toLowerCase() === j.name.toLowerCase(),
        ) < 0
      ) {
        invalidCategories.push(i.name)
      }
    })

    if (invalidCategories.length > 0) {
      setCategoriesValid(true)
    } else {
      setCategoriesValid(false)
    }
  }, [postState.categories, possibleCategories])

  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setPostState((postDto) => {
      return { ...postDto, title: event.target.value }
    })
  }

  const handleCategoriesChange = (event: ChangeEvent<HTMLInputElement>) => {
    setPostState((postDto) => {
      return {
        ...postDto,
        categories: event.target.value.split(',').map((i) => {
          return {
            id: possibleCategories.find(
              (c) => c.name.toLowerCase() === i.toLocaleLowerCase(),
            )?.id,
            name: i,
          }
        }),
      }
    })
  }

  const handleSummaryChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setPostState((postDto) => {
      return { ...postDto, summary: event.target.value }
    })
  }

  const handleTextChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setPostState((postDto) => {
      return { ...postDto, text: event.target.value }
    })
  }

  const handleVisibleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setPostState((postDto) => {
      return { ...postDto, visible: event.target.checked }
    })
  }

  const handleSave = async (event: FormEvent) => {
    event.preventDefault()

    if (!token) {
      toast.error('Token is not defined.')

      return
    }

    const result = await (post
      ? putPosts(token, postState)
      : postPosts(token, postState))

    if (result.message) {
      toast.error(result.message)
    } else {
      toast.success('Post saved.')
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
          value={postState.title}
        />
      </div>
      <div>
        <input
          type='text'
          placeholder='categories'
          onChange={handleCategoriesChange}
          value={postState.categories.map((i) => i.name)}
          className={categoriesValid ? styles.invalid : ``}
        />
      </div>
      <div>
        <textarea
          rows={5}
          placeholder='summary'
          onChange={handleSummaryChange}
          value={postState.summary}
        />
      </div>
      <div>
        <textarea
          rows={10}
          placeholder='text'
          onChange={handleTextChange}
          value={postState.text}
        />
      </div>
      {postState && (
        <Container>
          <Post post={postState} />
        </Container>
      )}
      <Container
        className={styles.options}
        name='Additional options'
        centered={false}
      >
        <div>
          <input
            type='checkbox'
            id='visible'
            onChange={handleVisibleChange}
            checked={postState.visible}
          />
          <label htmlFor='visible'>Post is visible</label>
        </div>
      </Container>
      <Container centered={true}>
        <Button text='Save' type='submit' />{' '}
      </Container>
    </form>
  )
}

export default EditPostForm
