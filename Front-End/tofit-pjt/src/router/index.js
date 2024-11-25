import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import SpecialistView from "@/views/SpecialistView.vue";
import VideoList from "@/components/video/VideoList.vue";
import VideoDetail from "@/components/video/VideoDetail.vue";
import VideoReviewList from "@/components/review/VideoReviewList.vue";
import SpecialistList from "@/components/specialist/SpecialistList.vue";
import UserLogin from "@/components/user/UserLogin.vue";
import SpecialistDetail from "@/components/specialist/SpecialistDetail.vue";
import UserSignup from "@/components/user/UserSignup.vue";
import FeedList from "@/components/feed/FeedList.vue";
import FeedView from "@/views/FeedView.vue";
import UserView from "@/views/UserView.vue";
import UserDetail from "@/components/user/UserDetail.vue";
import FeedCreate from "@/components/feed/FeedCreate.vue";
import RecordView from "@/views/RecordView.vue";
import RecordRegist from "@/components/record/RecordRegist.vue";
import CommunityView from "@/views/CommunityView.vue";
import FeedAllList from "@/components/feed/FeedAllList.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
      children: [
        {
          path: "",
          name: "videoList",
          component: VideoList,
          children: [],
        },
        {
          path: ":videoId",
          name: "videoDetail",
          component: VideoDetail,
          props: true,
          children: [
            {
              path: "",
              name: "videoReviewList",
              component: VideoReviewList,
            },
          ],
        },
      ],
    },
    {
      path: "/instructor",
      name: "specialist",
      component: SpecialistView,
      children: [
        {
          path: "",
          name: "specialistList",
          component: SpecialistList,
        },
        {
          path: ":specialistId",
          name: "specialistDetail",
          component: SpecialistDetail,
        },
      ],
    },
    {
      path: "/mypage",
      name: "mypage",
      component: UserView,
      children: [
        {
          path: "",
          name: "userDetail",
          component: UserDetail,
        },
      ],
    },
    {
      path: "/feed",
      name: "feed",
      component: FeedView,
      children: [
        {
          path: "",
          name: "feedList",
          component: FeedList,
        },
        {
          path: "write",
          name: "feedCreate",
          component: FeedCreate,
        },
      ],
    },
    {
      path: "/login",
      name: "login",
      component: UserLogin,
    },
    {
      path: "/signup",
      name: "signup",
      component: UserSignup,
    },
    {
      path: "/daily",
      name: "record",
      component: RecordView,
      children: [
        {
          path: "",
          name: "calendar",
          component: RecordRegist,
        },
      ],
    },
    {
      path: "/community",
      name: "community",
      component: CommunityView,
      children: [
        {
          path: "",
          name: "feedAllList",
          component: FeedAllList,
        },
      ],
    },
  ],
});

export default router;
