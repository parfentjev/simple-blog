import Container from '@/ui/layout/Container'
import Image from 'next/image'
import Link from 'next/link'
import { FC } from 'react'

const NotFoundErrorPage: FC<{}> = () => {
  return (
    <Container name='Oops!' centered={true}>
      <p>Apparently this page does not exist anymore!</p>
      <Image alt='Not Found' src='/404.png' width={512} height={512} />
      <p>
        <Link href='https://www.freepik.com' target='_blank'>
          Designed by Freepik
        </Link>
      </p>
    </Container>
  )
}

export default NotFoundErrorPage
