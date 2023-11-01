import { ProtectedRoute } from '@/store/auth-context'
import Link from 'next/link'
import { FC } from 'react'

const AdminPage: FC<{}> = () => {
  return (
    <ProtectedRoute>
      <ul>
        <li>
          <Link href='/admin/post'>Create a post</Link>
        </li>
        <li>
          <Link href='/admin/logout/'>Log out</Link>
        </li>
      </ul>
    </ProtectedRoute>
  )
}

export default AdminPage
