import CategoryDto from '@/api/models/CategoryDto'
import { FC } from 'react'
import Container from '../layout/Container'
import Link from 'next/link'

const Categories: FC<{ categories: CategoryDto[] }> = ({ categories }) => {
  return (
    <Container name='Categories'>
      <p>Select a category to see all posts that belong to it.</p>
      <ul>
        {categories.map((i) => (
          <li key={i.id}>
            <Link href={`/categories/${i.name}`}>{i.name}</Link>
          </li>
        ))}
      </ul>
    </Container>
  )
}

export default Categories
