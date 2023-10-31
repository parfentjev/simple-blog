import { ProtectedRoute } from '@/store/auth-context'
import Link from 'next/link'
import { FC } from 'react'

const AdminPage: FC<{}> = () => {
  return (
    <ProtectedRoute>
      <Link href='/admin/logout/'>Log out</Link>
    </ProtectedRoute>
  )
}

export default AdminPage
