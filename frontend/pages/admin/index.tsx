import { ProtectedRoute } from '@/store/auth-context'
import Container from '@/ui/layout/Container'
import Link from 'next/link'
import { FC } from 'react'

const AdminPage: FC<{}> = () => {
  return (
    <ProtectedRoute>
      <Container name='Admin page'>
        <ul>
          <li>
            <Link href='/admin/post'>Create a post</Link>
          </li>
          <li>
            <Link href='/admin/logout/'>Log out</Link>
          </li>
        </ul>
      </Container>
    </ProtectedRoute>
  )
}

export default AdminPage
