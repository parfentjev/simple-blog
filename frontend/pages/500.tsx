import Container from '@/ui/layout/Container'
import Image from 'next/image'
import Link from 'next/link'
import { FC } from 'react'

const InternalServerErrorPage: FC<{}> = () => {
  return (
    <Container name='Oops!' centered={true}>
      <p>Something went wrong.</p>
      <Image
        alt='Internal Server Error'
        src='/500.png'
        width={512}
        height={512}
      />
      <p>
        <Link href='https://www.freepik.com' target='_blank'>
          Designed by Freepik
        </Link>
      </p>
    </Container>
  )
}

export default InternalServerErrorPage