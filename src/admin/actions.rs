use std::error::Error;
use std::fmt::{Display, Formatter};

use anyhow::bail;
use diesel::prelude::*;
use uuid::Uuid;

use crate::admin::models::{NewUser, User, UserRequest};
use crate::schema::*;
use crate::admin::security::{generate_jwt, hash_password, password_valid};

fn registration_enabled(conn: &mut SqliteConnection) -> bool {
    matches!(users::table.count().get_result::<i64>(conn), Ok(count) if count == 0)
}

pub fn create_user(conn: &mut SqliteConnection, user_request: UserRequest) -> anyhow::Result<String> {
    if !registration_enabled(conn) {
        bail!(AdminActionError::RegistrationDisabled);
    }

    let user = diesel::insert_into(users::table)
        .values(NewUser {
            id: &Uuid::new_v4().to_string(),
            username: &user_request.username,
            password: &hash_password(&user_request.password)?,
        })
        .returning(User::as_returning())
        .get_result(conn)?;

    generate_jwt(user.id)
}

pub fn create_token(conn: &mut SqliteConnection, user_request: UserRequest) -> anyhow::Result<String> {
    let user = users::table.select(User::as_select())
        .filter(users::username.eq(user_request.username))
        .first(conn)
        .map_err(|_| AdminActionError::InvalidCredentials)?;

    if password_valid(&user_request.password, &user.password) {
        generate_jwt(user.id)
    } else {
        bail!(AdminActionError::InvalidCredentials)
    }
}

#[derive(Debug)]
pub enum AdminActionError {
    RegistrationDisabled,
    InvalidCredentials,
}

impl Display for AdminActionError {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        match self {
            Self::RegistrationDisabled => write!(f, "User registration is disabled."),
            Self::InvalidCredentials => write!(f, "Invalid username or password."),
        }
    }
}

impl Error for AdminActionError {}