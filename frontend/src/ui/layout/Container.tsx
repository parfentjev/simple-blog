import { FC, ReactNode } from 'react'

const Container: FC<{
  children: ReactNode
  name?: string
  centered?: boolean
  className?: string
}> = ({ children, name, centered, className }) => {
  return (
    <>
      <div className='text_centered'>{name && <h1>{name}</h1>}</div>
      <div
        className={
          (className ? className : ``) + ` ` + (centered ? `text_centered` : ``)
        }
      >
        <div>{children}</div>
      </div>
    </>
  )
}

export default Container
