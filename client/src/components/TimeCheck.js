export const TimeCheck = (time) => {
  const commentTime = new Date(Date.parse(time));
  const nowTime = Date.now();

  const differenceTime = nowTime - commentTime.getTime();
  const differenceDays = Math.floor(differenceTime / (1000 * 60 * 60 * 24));
  const differencehours = Math.floor(differenceTime / (60 * 60 * 1000));
  const differenceminutes = Math.floor(differenceTime / (60 * 1000));
  const differenceseconds = Math.floor(differenceTime / 1000);

  if (differenceDays > 30) {
    return commentTime.toLocaleString();
  } else if (differencehours > 24) {
    return differenceDays + ' days ago';
  } else if (differenceminutes > 60) {
    return differencehours + ' hours ago';
  } else if (differenceseconds > 60) {
    return differenceminutes + ' mins ago';
  } else {
    return differenceseconds + ' seconds ago';
  }
};
