-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tofit` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `tofit` ;

-- -----------------------------------------------------
-- Table `tofit`.`Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tofit`.`Category` (
  `code` INT NOT NULL,
  `goal` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE INDEX `goal_UNIQUE` (`goal` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tofit`.`Specialist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tofit`.`Specialist` (
  `specialist_id` VARCHAR(45) NOT NULL,
  `cate_code` INT NOT NULL,
  `hashtag` VARCHAR(200) NULL,
  `profile_img` VARCHAR(200) NULL,
  PRIMARY KEY (`specialist_id`),
  INDEX `fk_Specialist_Category1_idx` (`cate_code` ASC) INVISIBLE,
  CONSTRAINT `fk_Specialist_Category1`
    FOREIGN KEY (`cate_code`)
    REFERENCES `tofit`.`Category` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tofit`.`Video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tofit`.`Video` (
  `video_id` VARCHAR(100) NOT NULL,
  `channel_name` VARCHAR(45) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `thumbnail` VARCHAR(200) NOT NULL,
  `url` VARCHAR(200) NOT NULL,
  `cate_code` INT NOT NULL,
  `view_cnt` INT NULL DEFAULT 0,
  PRIMARY KEY (`video_id`),
  INDEX `fk_Video_Specialist_idx` (`channel_name` ASC) VISIBLE,
  INDEX `fk_Video_Category1_idx` (`cate_code` ASC) VISIBLE,
  CONSTRAINT `fk_Video_Specialist`
    FOREIGN KEY (`channel_name`)
    REFERENCES `tofit`.`Specialist` (`specialist_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Video_Category1`
    FOREIGN KEY (`cate_code`)
    REFERENCES `tofit`.`Category` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tofit`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tofit`.`User` (
  `user_id` VARCHAR(45) NOT NULL,
  `password` VARCHAR(200) NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  `gender` VARCHAR(45) NOT NULL,
  `birth` DATE NOT NULL,
  `profile_name` VARCHAR(45) NOT NULL,
  `profile_img` VARCHAR(45) NULL,
  `login_type` INT NOT NULL DEFAULT 0,
  `reg_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `profile_name_UNIQUE` (`profile_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tofit`.`Follows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tofit`.`Follows` (
  `follows_id` INT NOT NULL AUTO_INCREMENT,
  `follower_id` VARCHAR(45) NOT NULL,
  `followed_id` VARCHAR(45) NOT NULL,
  `checked` INT NOT NULL DEFAULT 0,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`follows_id`),
  UNIQUE INDEX `follows_UNIQUE` (`follower_id` ASC, `followed_id` ASC) VISIBLE,
   CONSTRAINT `fk_Follows_Follower`
    FOREIGN KEY (`follower_id`)
    REFERENCES `tofit`.`User` (`user_id`)
    ON DELETE CASCADE,  -- 팔로우 관계에서 사용자 삭제 시 관계도 삭제
  CONSTRAINT `fk_Follows_Followed`
    FOREIGN KEY (`followed_id`)
    REFERENCES `tofit`.`User` (`user_id`)
    ON DELETE CASCADE 
 )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tofit`.`Favorites`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tofit`.`Favorites` (
  `favorites_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `video_id` VARCHAR(45) NOT NULL,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`favorites_id`),
  UNIQUE INDEX `favorites_UNIQUE` (`user_id` ASC, `video_id` ASC) VISIBLE,
  INDEX `fk_Favorites_Video_idx` (`video_id` ASC) VISIBLE,
  CONSTRAINT `fk_Favorites_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `tofit`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Favorites_Video`
    FOREIGN KEY (`video_id`)
    REFERENCES `tofit`.`Video` (`video_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tofit`.`VideoReview`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tofit`.`VideoReview` (
  `review_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `video_id` VARCHAR(45) NOT NULL,
  `content` VARCHAR(500) NOT NULL,
  `reg_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  INDEX `fk_VideoReview_User1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_VideoReview_Video1_idx` (`video_id` ASC) VISIBLE,
  CONSTRAINT `fk_VideoReview_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `tofit`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_VideoReview_Video1`
    FOREIGN KEY (`video_id`)
    REFERENCES `tofit`.`Video` (`video_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tofit`.`Feed`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tofit`.`Feed` (
  `feed_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `content` TEXT NOT NULL,
  `reg_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`feed_id`),
  INDEX `fk_Feed_User1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_Feed_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `tofit`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tofit`.`FeedReview`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tofit`.`FeedReview` (
  `review_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `feed_id` INT NOT NULL,
  `content` VARCHAR(200) NOT NULL,
  `reg_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  INDEX `fk_FeedReview_Feed1_idx` (`feed_id` ASC) VISIBLE,
  INDEX `fk_FeedReview_User1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_FeedReview_Feed1`
    FOREIGN KEY (`feed_id`)
    REFERENCES `tofit`.`Feed` (`feed_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FeedReview_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `tofit`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tofit`.`FeedImage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tofit`.`FeedImage` (
  `image_id` INT NOT NULL AUTO_INCREMENT,
  `feed_id` INT NOT NULL,
  `img` VARCHAR(255) NULL,
  PRIMARY KEY (`image_id`),
  INDEX `fk_FeedImage_Feed1_idx` (`feed_id` ASC) VISIBLE,
  CONSTRAINT `fk_FeedImage_Feed1`
    FOREIGN KEY (`feed_id`)
    REFERENCES `tofit`.`Feed` (`feed_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tofit`.`Record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tofit`.`Record` (
  `record_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `video_id` VARCHAR(45) NOT NULL,
  `reg_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`),
  INDEX `fk_Record_Video1_idx` (`video_id` ASC) VISIBLE,
  INDEX `fk_Record_User1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_Record_Video1`
    FOREIGN KEY (`video_id`)
    REFERENCES `tofit`.`Video` (`video_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Record_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `tofit`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

# User Table
INSERT INTO User (user_id, password, email, gender, birth, profile_name, login_type) VALUES
('tester', 'tester', 'tester@example.com', 'M', '1990-05-15', 'tester', 0),
('user1', 'password1', 'user1@example.com', 'F', '1992-08-25', 'alpha', 0),
('user2', 'password2', 'user2@example.com', 'F', '1995-03-10', 'bella', 0),
('user3', 'password3', 'user3@example.com', 'M', '2001-12-20', 'charlie', 0),
('user4', 'password4', 'user4@example.com', 'F', '2003-07-14', 'delta', 0),
('user5', 'password5', 'user5@example.com', 'F', '2000-10-05', 'echo', 0),
('user6', 'password6', 'user6@example.com', 'F', '2002-04-22', 'foxy', 0),
('user7', 'password7', 'user7@example.com', 'M', '1999-06-18', 'gamma', 0),
('user8', 'password8', 'user8@example.com', 'F', '2005-01-30', 'honey', 0),
('user9', 'password9', 'user9@example.com', 'M', '2007-11-10', 'ivy', 0);

# Category Table
INSERT INTO `tofit`.`Category` (`code`, `goal`)
VALUES
(1, '다이어트'),
(2, '근력강화'),
(3, '체형교정'),
(4, '스트레칭'),
(5, '명상'),
(6, '식단');

INSERT INTO `tofit`.`Feed` (`user_id`, `content`) VALUES
('tester', '🔥 오늘 드디어 스쿼트 100개를 성공했어요! 처음에는 무릎이 아파서 걱정했지만, 점점 익숙해지더라고요. 운동 후 스트레칭을 충분히 했더니 💪 근육통도 덜했어요. 이렇게 조금씩 기록을 세워가니까 자신감이 생깁니다. ✨ 다음 목표는 스쿼트 150개 도전입니다! 여러분도 함께 도전해 보세요.'),
('tester', '다이어트 2주 차입니다. 체중은 크게 변하지 않았지만, 💡 확실히 몸이 가벼워진 느낌이 들어요. 특히 유산소 운동을 매일 30분씩 꾸준히 한 게 도움이 된 것 같아요. 식단 조절도 열심히 하고 있는데, 고구마와 닭가슴살 조합이 생각보다 맛있네요. 🥗 여러분은 다이어트 중 어떤 음식을 주로 드시나요?'),
('tester', '오늘 30분 동안 홈트레이닝 완수했어! 🏋️‍♀️ 스쿼트, 크런치, 플랭크 세트로 돌렸는데 땀이 정말 장난 아니더라 💦. 운동 끝나고 바나나랑 고구마 먹으니 완전 든든했어. 너도 요즘 열심히 한다고 들었는데, 어떤 루틴 하고 있어? 같이 운동 목표 공유해보자!'),
('tester', '드디어 다이어트 2주 차 성공! 🌟 몸무게는 많이 안 빠졌지만, 허리 라인이 좀 들어간 느낌이야. 오늘은 스트레칭으로 시작해서 유산소 루틴으로 마무리했어. 운동 영상 보면서 하니까 시간이 금방 가더라. 너도 힘내! 💪 꾸준히 하면 우리 모두 성공할 수 있을 거야.'),
('tester', '다이어트 시작한 지 벌써 50일째! 🎉 처음에는 정말 힘들었는데, 이제 운동이 일상이 됐어요. 체중도 5kg 줄었고, 거울 속 내 모습이 조금씩 달라지는 걸 보니 더 열심히 해야겠다는 생각이 듭니다. 목표까지 아직 3kg 남았지만, 천천히 가보려고요. 여러분도 포기하지 말고 함께 해봐요! 💯'),
('tester', '오늘은 다이어트 디데이 30일 남은 날! 💥 드디어 절반이나 왔습니다. 체중보다는 체형 변화를 보고 있어서 하루하루 운동이 너무 재밌어요. 아침에 스쿼트 100개로 시작했는데, 끝나고 나니 기분이 정말 좋더라고요. 끝까지 가보겠습니다!'),
('user1', '와 오늘은 완전 힘들었다. 20분 전신 루틴 돌렸는데 마지막에 플랭크에서 죽는 줄 알았어. 그래도 끝까지 해냈다! 🏋️ 이제 남은 건 꾸준히 하는 거겠지? 서로 응원하면서 힘내자! 다음 목표는 30분 도전이다~ 🔥'),
('user1', '오늘 아침에 정말 간단한 다이어트 요리를 만들어봤어요. 🍴 그릭 요거트에 견과류와 꿀을 넣어서 간단하게 먹었는데, 맛도 좋고 영양도 풍부하더라고요. 점심에는 닭가슴살 샐러드로 가볍게 먹었고, 저녁에는 고구마와 삶은 달걀로 마무리했습니다. 식단을 이렇게 정리하고 나니 몸이 한결 가벼워진 것 같아요. 여러분은 어떤 식단을 실천하고 있나요?'),
('user1', '드디어 3km 달리기 도전 성공했습니다! 🏃‍♀️ 집 주변에서 걸었다 뛰었다 반복하다 보니 기록이 점점 늘어나네요. 요즘은 기록하는 재미에 빠져서 매일 루틴을 수정하고 있어요. 다음 목표는 5km 도전하기! 같은 목표를 가진 분들 함께 화이팅해요~ 💪'),
('user1', '홈트 영상 보면서 상체 운동 열심히 따라 했어. 거울 보니까 어깨가 조금 탄탄해진 것 같더라! 💡 운동 끝나고 두유랑 삶은 달걀 먹으니까 피로가 풀리는 것 같아. 근데 너도 요즘 열심히 한다며? 다음엔 같이 루틴 공유해보자. 파이팅!'),
('user2', '다이어트 중간 점검! 체중은 2kg 줄었는데, 허리 라인이 확실히 달라졌어요. 👗 특히 옷 입었을 때 핏이 달라지는 걸 보니까 더 열심히 해야겠다는 생각이 들어요. 목표까지 1kg 남았는데, 마지막까지 최선을 다해보겠습니다. 다들 화이팅! 🔥'),
('user2', '오늘은 으뜸쌤 복부 집중 루틴으로 크런치랑 레그 레이즈를 3세트 돌렸어요. 🏋️‍♂️ 복부 운동할 때 진짜 힘들지만, 하고 나면 몸이 가벼워지는 기분이에요. 이제 꾸준히 하면 다음 달에는 복근 살짝 보이지 않을까요? 기록으로 남겨두고 싶어서 올려봅니다! ✨'),
('user2', '요즘 다이어트를 하면서 보조제를 병행하고 있어요. CLA와 녹차 추출물이 들어간 보조제를 먹고 있는데, 확실히 체지방 감소에 도움이 되는 것 같아요. 물론 보조제만 의존하지 않고 꾸준히 유산소 운동도 하고 있습니다. 아침에는 고구마와 계란으로, 점심에는 간단한 샐러드로 식단을 관리하고 있어요. 다들 어떤 보조제를 추천하시나요?'),
('user2', '요즘 다이어트 식단 조절하는데, 너무 힘들다. 오늘은 닭가슴살에 고구마랑 아보카도를 곁들여서 먹었어. 운동은 15분 유산소 루틴 따라 했고, 땀이 엄청 나더라. 너는 식단 어떻게 관리하고 있어? 추천할 만한 레시피 있으면 알려줘~'),
('user2', '오늘 복부 운동에 도전했어. 크런치랑 레그 레이즈 위주로 돌렸는데 땀이 비 오듯이 쏟아졌다! 아직 복근은 안 보이지만, 꾸준히 하면 나오겠지? 우리 다음에는 목표 공유하면서 같이 달려보자. 서로 응원하자고~ 너도 오늘 운동 했지?'),
('user3', '오늘은 복근 운동에 집중해봤어요. 플랭크와 크런치를 번갈아 가면서 했더니 땀이 정말 많이 나더라고요. 운동 후에는 물 한 잔과 단백질 쉐이크로 마무리했습니다. 사실 복근 운동은 힘들지만 꾸준히 하니까 조금씩 선이 보이는 것 같아요. 이렇게 변화하는 모습을 보면 더 열심히 해야겠다는 생각이 듭니다. 여러분은 어떤 복근 운동을 추천하시나요?'),
('user3', '드디어 플랭크 2분 돌파! 처음엔 30초도 못 버텼는데, 하루하루 조금씩 늘리다 보니 가능해졌어요. 다음 목표는 3분 도전인데, 여러분도 같이 목표 세워서 도전해보세요. 이렇게 기록 남기니까 동기부여가 더 잘 되는 것 같아요! 🌟'),
('user3', '운동하면서 가장 힘든 건 꾸준히 하는 것 같아요. 💡 그래서 요즘은 운동 스케줄을 적어두고 체크리스트를 사용하고 있어요. 오늘은 하체 운동으로 런지와 스쿼트를 했습니다. 운동 후에는 바나나와 단백질 쉐이크로 간단히 보충했어요. 이렇게 계획적으로 운동하니까 효과도 좋고 성취감도 커요. 다들 어떻게 운동 동기부여를 하시나요?'),
('user3', '오늘 집에서 점핑잭이랑 버피 테스트로 운동했어. 버피는 할 때마다 숨이 턱턱 막히는데, 하고 나면 엄청 개운해. 끝나고 단백질 쉐이크 마셨는데 초코 맛 완전 내 스타일! 너도 버피 해봤어? 진짜 추천이야. 같이 목표 세워보자!'),
('user3', '아침에 스트레칭으로 시작해서 요가로 마무리했어. 요가하면서 느끼는 건 호흡이 정말 중요하다는 거야. 몸이 한결 가벼워진 기분이야. 너도 요가 해봤어? 완전 강추야! 우리 요즘 홈트에 빠져있는데, 꾸준히 해서 꼭 목표 달성하자.'),
('user4', '오늘은 15분 간단 루틴으로 땀 쫙 빼고 끝냈어! 🏋️‍♀️ 스쿼트랑 점핑잭 조합으로 하면 집에서도 충분히 운동 효과 있더라. 운동 끝나고 고구마랑 삶은 계란으로 마무리하니까 완전 든든했어. 너도 꾸준히 하고 있는 거 알아! 같이 끝까지 해보자 🔥'),
('user4', '남은 체중 목표는 2kg! 정말 마지막이 어렵다는 걸 요즘 실감하고 있어요. 💪 매일 조금씩이라도 변화가 있다는 걸 믿고 끝까지 가보려고 합니다. 목표 달성하면 스스로에게 작은 선물을 줄 생각이에요. 여러분은 목표 달성 후 어떤 계획이 있나요?'),
('user4', '친구들이랑 홈트 챌린지 시작했는데, 오늘은 플랭크 3분 도전 성공했다! 정말 힘들었지만, 하고 나니까 뿌듯하더라. 너도 플랭크 해봤어? 점점 시간이 늘어나는 게 진짜 재밌어. 우리 이번 주에 5분까지 도전해보자. 같이 화이팅하자~ 🔥'),
('user4', '오늘은 휴식하는 날! 하지만 그냥 쉬기엔 아쉬워서 가볍게 스트레칭으로 몸을 풀었어요. 요즘은 운동 루틴을 정리하면서 하루하루 목표를 세우는 재미가 생겼어요. 작년에는 절대 못할 거라 생각했는데, 지금은 이렇게 꾸준히 하고 있는 내가 뿌듯합니다!');

INSERT INTO `tofit`.`FeedReview` (`user_id`, `feed_id`, `content`) VALUES
('user5', 3, '오늘도 운동하다니 정말 대단하다 ㅠㅠ'),
('user5', 2, '몸 관리 어떻게 하셨나요? 너무 부럽습니다!'),
('user5', 2, '동기부여가 되는 글이네요. 꼭 따라 해볼게요!'),
('user6', 2, '운동 루틴 공유해 주셔서 감사합니다!'),
('user6', 1, '따라 하기 쉬운 동작들이라 좋아요. 매일 할게요!'),
('user6', 3, '정말 땀나는 운동이네요! 저도 해볼게요!'),
('user7', 2, '관리 하기 좋은 식단은 없을까요?'),
('user7', 3, '층간소음 걱정 없이 운동할 수 있어서 너무 좋습니다!'),
('user7', 1, '시간이 없었는데 딱 맞는 루틴이네요. 감사합니다!'),
('user8', 1, '운동 처음 시작하는 사람도 할 수 있어서 좋네요!'),
('user8', 1, '이 루틴 보고 정말 많이 배웠습니다. 감사합니다!'),
('user8', 2, '운동 루틴이 다양해서 좋아요. 계속 따라 하고 싶어요!'),
('user9', 2, '오늘 이 글 덕분에 하루를 잘 보냈어요. 감사합니다!'),
('user9', 1, '저도 운동 후 땀이 정말 많이 나네요. 기분이 좋습니다!'),
('user9', 1, '좋은 운동법 소개해 주셔서 감사합니다! 꾸준히 할게요!');

INSERT INTO `tofit`.`Specialist` 
(`specialist_id`, `cate_code`, `hashtag`) 
VALUES
('땅끄부부', 1, '#다이어트 #홈트 #칼로리불태우기 #전신운동'),
('비타민신지니 VitaminJINY', 1, '#홈트 #체중감량 #뱃살타파'),
('흥둥이', 1, '#다이어트 #댄스 #아이돌'),
('Allblanc TV', 1, '#헬스 #체지방 삭제 #운동루틴 #땀폭발'),
('힙으뜸', 2, '#근력운동 #슬림탄탄 #하루10분 #챌린지'),
('이지은 다이어트 Jiny diet', 3, '#라인 재창조 #상체운동 #슬림'),
('제이제이살롱드핏', 3, '#체형 변화 #초보자 #팔뚝살'),
('강하나 스트레칭_stretching', 4, '#부종제거 #스트레칭 #피로 풀기');

# Video Table
INSERT INTO `tofit`.`Video` 
(`video_id`, `channel_name`, `title`, `thumbnail`, `url`, `cate_code`, `view_cnt`) 
VALUES
('DCAp0b16kyo', '땅끄부부', '전신 칼로리 다이어트 최고의 운동', 'https://i.ytimg.com/vi/DCAp0b16kyo/hqdefault.jpg', 'https://www.youtube.com/watch?v=DCAp0b16kyo', 1, 3542),
('swRNeYw1JkY', '땅끄부부', '하루 15분! 전신 칼로리 불태우는 다이어트 운동', 'https://i.ytimg.com/vi/swRNeYw1JkY/hqdefault.jpg', 'https://www.youtube.com/watch?v=swRNeYw1JkY', 1, 8301),
('VNQpP6C1fJg', '땅끄부부', '집에서 하는 유산소운동 다이어트 [칼소폭]', 'https://i.ytimg.com/vi/VNQpP6C1fJg/hqdefault.jpg', 'https://www.youtube.com/watch?v=VNQpP6C1fJg', 1, 6193),
('F-Jd4kI6rdM', '땅끄부부', '전신 다이어트 최고의 운동 [칼소폭 마라맛🔥]', 'https://i.ytimg.com/vi/F-Jd4kI6rdM/hqdefault.jpg', 'https://www.youtube.com/watch?v=F-Jd4kI6rdM', 1, 4152),
('ZW1pnq0b8Hs', '땅끄부부', '걷기♥', 'https://i.ytimg.com/vi/ZW1pnq0b8Hs/hqdefault.jpg', 'https://www.youtube.com/watch?v=ZW1pnq0b8Hs', 1, 2731),
('t70t-sklypk', '땅끄부부', '집에서 칼로리 불태우는 걷기 유산소운동 [칼소폭 순한맛]', 'https://i.ytimg.com/vi/t70t-sklypk/hqdefault.jpg', 'https://www.youtube.com/watch?v=t70t-sklypk', 1, 9654),
('lKwZ2DU4P-A', '땅끄부부', '집에서 칼로리 불태우는 최고의 유산소운동 [칼소폭 매운맛]', 'https://i.ytimg.com/vi/lKwZ2DU4P-A/hqdefault.jpg', 'https://www.youtube.com/watch?v=lKwZ2DU4P-A', 1, 2184),
('PoYzxj8Iy5M', '땅끄부부', '1분 다이어트 함께해요♥', 'https://i.ytimg.com/vi/PoYzxj8Iy5M/hqdefault.jpg', 'https://www.youtube.com/watch?v=PoYzxj8Iy5M', 1, 6547),
('hQiuOOuu46o', '땅끄부부', '전신 다이어트 최고의 운동 [칼소폭 핵 마라맛🔥🔥]', 'https://i.ytimg.com/vi/hQiuOOuu46o/hqdefault.jpg', 'https://www.youtube.com/watch?v=hQiuOOuu46o', 1, 9785),
('wrLlzn5TjLY', '땅끄부부', '집에서 하는 유산소운동 다이어트 [칼소폭 플러스] 30 MIN FULL BODY WORKOUT', 'https://i.ytimg.com/vi/wrLlzn5TjLY/hqdefault.jpg', 'https://www.youtube.com/watch?v=wrLlzn5TjLY', 1, 8051),
('3VouSaW_LPw', '땅끄부부', '전신 다이어트 유산소운동 [홈트레이닝]', 'https://i.ytimg.com/vi/3VouSaW_LPw/hqdefault.jpg', 'https://www.youtube.com/watch?v=3VouSaW_LPw', 1, 1690),
('gMaB-fG4u4g', '땅끄부부', '전신 다이어트 최고의 운동 [칼소폭 찐 핵핵 매운맛]', 'https://i.ytimg.com/vi/gMaB-fG4u4g/hqdefault.jpg', 'https://www.youtube.com/watch?v=gMaB-fG4u4g', 1, 5583),
('CTojmKLkWTo', '땅끄부부', '전신 다이어트 최고의 운동 [칼소폭 핵매운맛]', 'https://i.ytimg.com/vi/CTojmKLkWTo/hqdefault.jpg', 'https://www.youtube.com/watch?v=CTojmKLkWTo', 1, 7777),
('UYHfk45Yi2c', '땅끄부부', '상체(팔뚝살)♥', 'https://i.ytimg.com/vi/UYHfk45Yi2c/hqdefault.jpg', 'https://www.youtube.com/playlist?list=PLPPetu1spkeaNfn2ANS53rcyPCP9sfvyD', 1, 3402),
('gSz5n4sLENI', '땅끄부부', '전신 다이어트 최고의 운동 [칼소폭 찐 핵핵핵 매운맛]', 'https://i.ytimg.com/vi/gSz5n4sLENI/hqdefault.jpg', 'https://www.youtube.com/watch?v=gSz5n4sLENI', 1, 8762),
('t4zAnbl2E6I', '흥둥이', '[에스파-위플래쉬 다이어트댄스] 비트가 미쳤다는 쇠맛..레전드 동작으로 체지방 빠르게 빼기🔥', 'https://i.ytimg.com/vi/t4zAnbl2E6I/hqdefault.jpg', 'https://www.youtube.com/watch?v=t4zAnbl2E6I', 1, 2119),
('TtrwUOYgJqI', '흥둥이', 'ROSE &amp; Bruno Mars (로제&amp;브루노마스)- APT. (아파트) | 살이 쭉쭉 빠지는 다이어트댄스로 -10kg도전🔥', 'https://i.ytimg.com/vi/TtrwUOYgJqI/hqdefault.jpg', 'https://www.youtube.com/watch?v=TtrwUOYgJqI', 1, 4643),
('oQUwOgNp4os', '흥둥이', '‘요청폭주’ 2세대 아이돌 다이어트 댄스 모음집 역시...댄스는 2세대가 레전드 2주 -5kg 도전🔥', 'https://i.ytimg.com/vi/oQUwOgNp4os/hqdefault.jpg', 'https://www.youtube.com/watch?v=oQUwOgNp4os', 1, 2082),
('d1j06bW5VO4', '흥둥이', '[여자아이돌 다이어트댄스]실제 감량후기 폭발! 4세대 여돌 플리로 2주 -5kg 도전해보세요🔥', 'https://i.ytimg.com/vi/d1j06bW5VO4/hqdefault.jpg', 'https://www.youtube.com/watch?v=d1j06bW5VO4', 1, 9324),
('sAphP_KZkHE', '흥둥이', '[남돌 다이어트 댄스 모음] 살 쭉 빠졌다는 실제 후기가 가득한 다이어트댄스, 딱 2주만 도전 해보세요 🔥', 'https://i.ytimg.com/vi/sAphP_KZkHE/hqdefault.jpg', 'https://www.youtube.com/watch?v=sAphP_KZkHE', 1, 1024),
('zso6euxDEFM', '비타민신지니 VitaminJINY', '와...내가 이래서 살이 안 빠졌었구나...', 'https://i.ytimg.com/vi/zso6euxDEFM/hqdefault.jpg', 'https://www.youtube.com/watch?v=zso6euxDEFM', 1, 6253),
('unw2TmqsWC8', '비타민신지니 VitaminJINY', '장원영 몸매가 이쁠 수 밖에 없는 이유.', 'https://i.ytimg.com/vi/unw2TmqsWC8/hqdefault.jpg', 'https://www.youtube.com/watch?v=unw2TmqsWC8', 1, 9843),
('UjQecB-PDDg', '비타민신지니 VitaminJINY', '1번만 따라해도 몸무게가 확 줄어드는 ‘역대급 체중감량운동’ (급속버전🔥)', 'https://i.ytimg.com/vi/UjQecB-PDDg/hqdefault.jpg', 'https://www.youtube.com/watch?v=UjQecB-PDDg', 1, 8000),
('qQqhAy8n2J4', '비타민신지니 VitaminJINY', '[먹고싶은 음식 다 먹으면서 살빼는법]]', 'https://i.ytimg.com/vi/qQqhAy8n2J4/hqdefault.jpg', 'https://www.youtube.com/watch?v=qQqhAy8n2J4', 1, 15000),
('F15ANDUjPjQ', '비타민신지니 VitaminJINY', '요즘 해외에서 4일만에 뱃살 빠진다고 소문난 루틴 (후기가 대박👍)', 'https://i.ytimg.com/vi/F15ANDUjPjQ/hqdefault.jpg', 'https://www.youtube.com/watch?v=F15ANDUjPjQ', 1, 20000),

('3P0BTl5jKdE', 'Allblanc TV', '땀폭발! 흥폭발! 50분 전신 운동l 50MIN BEGINNER HIIT WORKOUT - No Repeat, No Equipment @shaketwins', 'https://i.ytimg.com/vi/3P0BTl5jKdE/default.jpg', 'https://www.youtube.com/watch?v=3P0BTl5jKdE', 1, 1542),
('N-15wUPnqpc', 'Allblanc TV', '하루 10분! 전신 칼로리 불태우는 다이어트 홈트레이닝 l Do This Everyday To Lose Weight (10 MIN FULLBODY WORKOUT AT HOME)', 'https://i.ytimg.com/vi/N-15wUPnqpc/default.jpg', 'https://www.youtube.com/watch?v=N-15wUPnqpc', 1, 344),
('Oto2uvnrMXE', 'Allblanc TV', '[흥폭발💦] 묵은 체지방 삭제시켜주는 50분 전신 운동 | 50MIN FAT BURNING FULLBODY WORKOUT @shaketwins', 'https://i.ytimg.com/vi/Oto2uvnrMXE/default.jpg', 'https://www.youtube.com/watch?v=Oto2uvnrMXE', 1, 4248),
('NEukbpmQ-pY', 'Allblanc TV', '20MIN SWEATY HIIT WORKOUT - No Equipment, Fat-burning, Fullbody l 전신 땀범벅 20분 고강도 홈트  @shaketwins', 'https://i.ytimg.com/vi/NEukbpmQ-pY/default.jpg', 'https://www.youtube.com/watch?v=NEukbpmQ-pY', 1, 192),
('4gVDWclAEkI', 'Allblanc TV', '[체지방 삭제✂️] 멈출 수 없어요! 흥나는 50분 전신 운동 | 50MIN FAT BURNING WORKOUT with @shaketwins', 'https://i.ytimg.com/vi/4gVDWclAEkI/default.jpg', 'https://www.youtube.com/watch?v=4gVDWclAEkI', 1, 320),
('WfdXMj9I7H0', 'Allblanc TV', 'Do This Everyday To Lose Weight (No Gym Fullbody Tabata)ㅣ10분만에 체지방 불태우는 타바타 운동', 'https://i.ytimg.com/vi/WfdXMj9I7H0/default.jpg', 'https://www.youtube.com/watch?v=WfdXMj9I7H0', 1, 1548),

('zruKSHfD7b4', '힙으뜸', '[ENG] 딱 20분🔥 하루 운동 끝장내는 전신 & 아랫배 & 타바타 올인원 루틴', 'https://i.ytimg.com/vi/zruKSHfD7b4/default.jpg', 'https://www.youtube.com/watch?v=zruKSHfD7b4', 2, 0),
('pGpnulkC5z8', '힙으뜸', '[ENG] 🔥30분 땀폭발 루틴🔥 2주만에 슬림탄탄 몸 만들기! 힙으뜸 복근 유산소 30분 홈트레이닝', 'https://i.ytimg.com/vi/pGpnulkC5z8/default.jpg', 'https://www.youtube.com/watch?v=pGpnulkC5z8', 2, 0),
('46vQnzaZ6aU', '힙으뜸', '[ENG] (층간소음 X) 진짜 역대급 20분 루틴ㅣ전신올인원 I 힙으뜸 유산소 운동', 'https://i.ytimg.com/vi/46vQnzaZ6aU/default.jpg', 'https://www.youtube.com/watch?v=46vQnzaZ6aU', 2, 0),
('kETh8T3it4k', '힙으뜸', '[ENG] (층간소음X, 설명O) 복근운동과 유산소를 한번에❗️서서하는 복근운동 1탄🔥', 'https://i.ytimg.com/vi/kETh8T3it4k/default.jpg', 'https://www.youtube.com/watch?v=kETh8T3it4k', 2, 0),
('QSZ0mUGO_CA', '힙으뜸', '[ENG] (층간소음X, 설명O) 🔥짧고 굵게🔥 복근만들기 2주 챌린지', 'https://i.ytimg.com/vi/QSZ0mUGO_CA/default.jpg', 'https://www.youtube.com/watch?v=QSZ0mUGO_CA', 2, 0),

('z3zp5U27mQg', '이지은 다이어트 Jiny diet', '툭튀 승마살 제거하고 \'로우라이즈 여신\' 되는 골반 루틴✨ (feat. 유나골반)', 'https://i.ytimg.com/vi/z3zp5U27mQg/hqdefault.jpg', 'https://www.youtube.com/watch?v=z3zp5U27mQg', 3, 10000),
('yhD3xhvPz6s', '이지은 다이어트 Jiny diet', '✨얼굴라인 재창조 스킬✨(일찍 알면 좋았을 걸..!)', 'https://i.ytimg.com/vi/yhD3xhvPz6s/hqdefault.jpg', 'https://www.youtube.com/watchv=yhD3xhvPz6s', 3, 15000),
('M3bVPN42Kdo', '이지은 다이어트 Jiny diet', '승모근 소멸🔥 오프숄더 여신되는 ‘제니어깨’ 루틴', 'https://i.ytimg.com/vi/M3bVPN42Kdo/hqdefault.jpg', 'https://www.youtube.com/watch?v=M3bVPN42Kdo', 3, 12000),
('doiSCQsxV58', '이지은 다이어트 Jiny diet', '침대에 누워서 딱 5분으로, \'종아리에 광명\'을 경험하세요✨️', 'https://i.ytimg.com/vi/doiSCQsxV58/hqdefault.jpg', 'https://www.youtube.com/watch?v=doiSCQsxV58', 3, 8000),
('dpBYYEhdofI', '이지은 다이어트 Jiny diet', '앞벅지 볼록, 뒷벅지 셀룰라이트, 허벅지 안쪽살 모조리 불태우고🔥 [여리탄탄 일자 허벅지] 되는 7일 루틴', 'https://i.ytimg.com/vi/dpBYYEhdofI/hqdefault.jpg', 'https://www.youtube.com/watch?v=dpBYYEhdofI', 3, 20000),
('43vRsKyyIns', '이지은 다이어트 Jiny diet', '♦️볼록 앞벅지, 무릎통증, 다리부기♦️ 모조리 해결하고 \'일자다리\' 만드는 10분 하체교정 루틴', 'https://i.ytimg.com/vi/43vRsKyyIns/hqdefault.jpg', 'https://www.youtube.com/watch?v=43vRsKyyIns', 3, 25000),
('m1LEbxtuWK4', '이지은 다이어트 Jiny diet', '볼록한 앞벅지를 날씬하게 만들어주는 슬림 스트레칭 루틴', 'https://i.ytimg.com/vi/m1LEbxtuWK4/hqdefault.jpg', 'https://www.youtube.com/watch?v=m1LEbxtuWK4', 3, 30000),

('kpGZlmdn5CQ', '제이제이살롱드핏', '내가 운동으로 체형을 바꿀 수 있었던 결정적 이유 (이걸 모르면 절대 안 바뀜)', 'https://i.ytimg.com/vi/kpGZlmdn5CQ/hqdefault.jpg', 'https://www.youtube.com/watch?v=kpGZlmdn5CQ', 3, 15000),
('1cRflgUwVxQ', '제이제이살롱드핏', '여자가 하체운동해도 허벅지 근육이 이정도밖에 안 생겨요', 'https://i.ytimg.com/vi/1cRflgUwVxQ/hqdefault.jpg', 'https://www.youtube.com/watch?v=1cRflgUwVxQ', 3, 12000),
('QdGhed11XCQ', '제이제이살롱드핏', '3D 엉덩이 만드는 헬스장 루틴', 'https://i.ytimg.com/vi/QdGhed11XCQ/hqdefault.jpg', 'https://www.youtube.com/watch?v=QdGhed11XCQ', 3, 18000),
('nM-W7dJtcXc', '제이제이살롱드핏', '(몸매흑역사) 이랬던몸이 바뀌었어요! 체형별 꼭 해야하는 다이어트 운동 BEST', 'https://i.ytimg.com/vi/nM-W7dJtcXc/hqdefault.jpg', 'https://www.youtube.com/watch?v=nM-W7dJtcXc', 3, 20000),
('KVVaS_g8DrI', '제이제이살롱드핏', '운동으로 쳐진가슴 업시키기', 'https://i.ytimg.com/vi/KVVaS_g8DrI/hqdefault.jpg', 'https://www.youtube.com/watch?v=KVVaS_g8DrI', 3, 25000),
('GUbos2yeFIY', '제이제이살롱드핏', '초보자도 쉽게 따라할 수 있는 골반라인 만드는 방법', 'https://i.ytimg.com/vi/GUbos2yeFIY/hqdefault.jpg', 'https://www.youtube.com/watch?v=GUbos2yeFIY', 3, 17000),
('JduI8vCpoE4', '제이제이살롱드핏', '유형 별 다이어트 방법, 이 단계에선 절대 이 방법은 쓰지 마세요!', 'https://i.ytimg.com/vi/JduI8vCpoE4/hqdefault.jpg', 'https://www.youtube.com/watch?v=JduI8vCpoE4', 3, 10000),

('yZ6zD3TY6eI', '강하나 스트레칭_stretching', '한 번하면 시원함에 다시 또 하게된다는 하체부종 5분!!', 'https://i.ytimg.com/vi/yZ6zD3TY6eI/hqdefault.jpg', 'https://www.youtube.com/watch?v=yZ6zD3TY6eI', 4, 4823),
('4UhHQ2OxvFI', '강하나 스트레칭_stretching', '한 번만에 바로 효과보는!! 뭉친 승모근 없애는 스트레칭 - Neck and Shoulder Pain Relief - 강하나 스트레칭(2018)', 'https://i.ytimg.com/vi/4UhHQ2OxvFI/hqdefault.jpg', 'https://www.youtube.com/watch?v=4UhHQ2OxvFI', 4, 1274),
('Q-C_NUKQr9g', '강하나 스트레칭_stretching', '자기전 스트레칭(GOOD NIGHT STRETCHING) - 강하나 스트레칭(2018)', 'https://i.ytimg.com/vi/Q-C_NUKQr9g/hqdefault.jpg', 'https://www.youtube.com/watch?v=Q-C_NUKQr9g', 4, 3291),
('djnCTOctbrY', '강하나 스트레칭_stretching', '전신의 피로를 풀어 깃털처럼 가벼워지는 전신스트레칭', 'https://i.ytimg.com/vi/djnCTOctbrY/hqdefault.jpg', 'https://www.youtube.com/watch?v=djnCTOctbrY', 4, 5542),
('eRpPqnmb0PM', '강하나 스트레칭_stretching', '매일 5분만 해서 팔뚝살빼자!! 딱 5분 투자 최대효과', 'https://i.ytimg.com/vi/eRpPqnmb0PM/hqdefault.jpg', 'https://www.youtube.com/watch?v=eRpPqnmb0PM', 4, 8773);

-- VideoReview
INSERT INTO `tofit`.`VideoReview` (`user_id`, `video_id`, `content`) 
VALUES
('tester', 'DCAp0b16kyo', '이 운동 영상 덕분에 다이어트에 큰 도움이 되었습니다. 간단하고 효율적인 운동이었어요!'),
('user1', 'DCAp0b16kyo', '정말 좋네요! 몸이 가벼워졌고, 운동 후 기분도 정말 좋습니다. 지속적으로 해야겠어요.'),
('user2', 'DCAp0b16kyo', '처음에는 힘들었는데 점점 몸이 적응되면서 효과가 느껴집니다. 감사합니다!'),
('user3', 'swRNeYw1JkY', '하루 15분! 전신 칼로리 불태우는 다이어트 운동이 정말 좋아요. 매일 할게요!'),
('user4', 'swRNeYw1JkY', '간단하고 효과적인 운동이네요. 처음에는 조금 힘들었지만 점점 좋아졌어요.'),
('user5', 'swRNeYw1JkY', '15분이면 충분히 다이어트를 시작할 수 있어서 좋습니다. 꾸준히 할게요!'),
('user6', 'VNQpP6C1fJg', '집에서 할 수 있는 유산소 운동이라서 너무 좋아요! 땀이 나면서 체중 감량도 기대돼요.'),
('user7', 'VNQpP6C1fJg', '다이어트에 도움이 되는 유산소 운동! 집에서 편하게 할 수 있어서 좋습니다.'),
('user8', 'VNQpP6C1fJg', '유산소 운동을 집에서 할 수 있다는 점이 마음에 들어요. 날씨 상관 없이 할 수 있어서 편리합니다.'),
('user9', 'F-Jd4kI6rdM', '칼소폭 마라맛! 정말 강력하지만 그만큼 효과도 있는 운동입니다. 최고에요!'),
('tester', 'F-Jd4kI6rdM', '매운맛 운동이라 힘들었지만, 땀을 흘리면서 운동하는 기분이 너무 좋았습니다.'),
('user1', 'F-Jd4kI6rdM', '칼소폭 운동이 정말 대박이에요! 전신을 단련할 수 있어서 좋습니다.'),
('user2', 'ZW1pnq0b8Hs', '간단한 걷기 운동인데도 몸이 개운해지네요. 꾸준히 해야겠어요!'),
('user3', 'ZW1pnq0b8Hs', '걷기 운동이 이렇게 효과가 좋을 줄 몰랐어요. 편안하면서도 좋습니다.'),
('user4', 'ZW1pnq0b8Hs', '집에서 쉽게 할 수 있어서 너무 좋아요. 다른 운동과 병행하면 더 효과적일 것 같습니다.'),
('user5', 't70t-sklypk', '걷기 운동이 이렇게 재밌을 수 있다는 걸 몰랐어요. 칼로리 불태우기에 최고입니다!'),
('user6', 't70t-sklypk', '쉬운 운동이라 다들 할 수 있을 것 같아요. 땀도 많이 나고 만족스럽습니다.'),
('user7', 't70t-sklypk', '운동 후 기분이 상쾌해지고, 체중 감량에 큰 도움이 될 것 같아요. 꾸준히 할 예정입니다.'),
('user8', 'lKwZ2DU4P-A', '매운맛 운동이라면서 그렇게 힘든 건 아니었어요. 하지만 효과는 확실할 것 같아요!'),
('user9', 'lKwZ2DU4P-A', '매운맛 운동이 생각보다 재미있어요. 칼로리 소모도 잘 될 것 같고 만족합니다.'),
('tester', 'lKwZ2DU4P-A', '매운맛 운동이라서 부담이 있었지만, 할만하네요. 체력 향상에 좋을 것 같습니다.'),
('user1', 'PoYzxj8Iy5M', '1분 운동이라서 짧고 간편해서 좋네요. 시간이 없을 때 유용하게 할 수 있어요.'),
('user2', 'PoYzxj8Iy5M', '간단한 운동이지만 효과가 좋습니다. 바쁜 일상에 딱 맞는 운동이에요!'),
('user3', 'PoYzxj8Iy5M', '짧은 시간에 운동할 수 있어서 너무 편리해요. 다른 운동과 함께 해도 좋을 것 같습니다.'),
('user4', 'hQiuOOuu46o', '핵 마라맛! 정말 강력한 운동이네요. 다이어트에 큰 도움이 될 것 같습니다.'),
('user5', 'hQiuOOuu46o', '운동이 정말 힘들지만 그만큼 땀이 많이 나고 효과는 확실할 것 같아요.'),
('user6', 'hQiuOOuu46o', '강력한 전신 운동이라 칼로리가 확실히 소모되는 느낌이에요. 계속 해야겠습니다.'),
('tester', 'TtrwUOYgJqI', '이 운동 덕분에 진짜 다이어트 효과를 봤어요! -5kg 뺐어요!'),
('user1', 'TtrwUOYgJqI', '저는 이 춤 따라하면서 즐겁게 운동했어요. 계속 하다보면 더 효과 있을 것 같아요!'),
('user2', 'TtrwUOYgJqI', '이 영상 보고 도전했는데, 의외로 힘들어서 좋았어요. 계속 하니까 점점 나아지네요.'),
('user3', 'TtrwUOYgJqI', '처음에는 힘들었는데 점점 재밌어져요. 효과는 조금씩 나고 있어요!'),
('user4', 'TtrwUOYgJqI', '20분 안에 이렇게 힘들 줄 몰랐어요. 땀 범벅 되네요. 계속 해볼게요!'),
('user5', 'oQUwOgNp4os', '아이돌 댄스 너무 재밌고 신나요! 2주 동안 따라 해보겠습니다.'),
('user6', 'oQUwOgNp4os', '2세대 아이돌 댄스는 언제 봐도 멋있네요. 효과도 좋을 것 같아요!'),
('user7', 'oQUwOgNp4os', '추억의 댄스를 다시 해보니 너무 좋네요. 운동 효과도 있을 것 같아요!'),
('user8', 'oQUwOgNp4os', '추억 돋네요~ 이 춤 정말 좋아요! 운동도 되고 신나네요!'),
('user9', 'oQUwOgNp4os', '춤을 추면서 다이어트라니 정말 재밌어요. 계속 따라 할게요!'),
('user1', 'd1j06bW5VO4', '4세대 아이돌 댄스를 따라 하니까 확실히 힘들어요. 하지만 재미있어요!'),
('user2', 'd1j06bW5VO4', '이 댄스는 정말 체력적으로 힘든데 끝나면 뿌듯함이 있어요!'),
('user3', 'd1j06bW5VO4', '처음엔 힘들었는데 점차 나아지고 있어요. 2주 동안 열심히 해볼게요.'),
('user4', 'd1j06bW5VO4', '다이어트댄스 진짜 재밌고 효과 있을 것 같아요! 2주간 열심히 따라 해보겠습니다.'),
('user5', 'd1j06bW5VO4', '여자아이돌 댄스라니 너무 좋아요! 운동하면서 즐길 수 있어서 좋습니다.'),
('user6', 'sAphP_KZkHE', '남돌 댄스도 다이어트에 효과적이네요! 즐기면서 할 수 있어서 좋아요.'),
('user7', 'sAphP_KZkHE', '2주 동안 하면 정말 효과 있을 것 같아요. 재밌어서 꾸준히 할 수 있겠어요!'),
('user8', 'sAphP_KZkHE', '남돌 댄스를 이렇게 따라 해도 되는 건가요? 너무 즐겁고 좋네요!'),
('user9', 'sAphP_KZkHE', '이 댄스를 하니 체력이 늘고 있는 것 같아요. 재미있게 따라 하고 있어요!'),
('user5', 'sAphP_KZkHE', '체중 감량을 위해서 이 댄스를 시작해봤어요. 흥미롭고 즐거운 운동입니다!'),
('user1', 'zso6euxDEFM', '와, 이런 이유에서 살이 안 빠졌구나! 제대로 된 운동법을 알게 되었어요.'),
('user2', 'zso6euxDEFM', '이 영상을 보고 식단과 운동을 다시 생각하게 되었어요. 감사합니다!'),
('user3', 'zso6euxDEFM', '진짜 중요한 포인트를 짚어주셔서 감사합니다. 다이어트에 도움이 될 것 같아요.'),
('user4', 'zso6euxDEFM', '이렇게 자세히 설명해주시니까 이해가 잘 되네요. 실천해 볼게요!'),
('user5', 'zso6euxDEFM', '비디오에서 알려준 방법을 실천했더니 조금씩 변화가 있어요. 고마워요!'),
('user6', 'unw2TmqsWC8', '장원영씨의 몸매 비결 정말 흥미롭네요. 저도 따라 해봐야겠어요!'),
('user7', 'unw2TmqsWC8', '장원영 몸매를 위해서 뭘 해야 하는지 알게 되어 좋았어요!'),
('user8', 'unw2TmqsWC8', '이렇게 노력하면 정말 몸매가 좋아지겠네요. 다이어트 의지가 생깁니다!'),
('user9', 'unw2TmqsWC8', '몸매 비결을 알게 되어 좋은 정보였습니다. 앞으로도 열심히 해볼게요!'),
('user5', 'unw2TmqsWC8', '장원영씨처럼 되려면 꾸준히 운동과 관리가 필요하겠어요. 시작해볼게요!'),
('user1', 'UjQecB-PDDg', '이 운동만으로도 확실히 체중이 줄어드는 기분이에요! 계속 할 거예요.'),
('user2', 'UjQecB-PDDg', '급속 버전이라서 정말 힘든데 그만큼 효과가 있을 것 같아요. 열심히 해볼게요!'),
('user3', 'UjQecB-PDDg', '급속체중감량운동, 땀도 많이 나고 너무 힘든데 효과는 있을 것 같아요.'),
('user4', 'UjQecB-PDDg', '이 운동은 진짜 빠르게 체중을 줄여줄 수 있을 것 같아요. 다이어트 필수 운동입니다!'),
('user5', 'UjQecB-PDDg', '진짜 운동 시작한지 얼마 안 됐는데 벌써 효과가 조금씩 보이네요. 계속 할게요!');


