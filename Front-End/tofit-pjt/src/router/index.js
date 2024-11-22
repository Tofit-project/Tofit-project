import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import SpecialistView from "@/views/SpecialistView.vue";
import VideoList from "@/components/video/VideoList.vue";
import VideoDetail from "@/components/video/VideoDetail.vue";
import VideoReviewList from "@/components/review/VideoReviewList.vue";
import SpecialistList from "@/components/specialist/SpecialistList.vue";
import UserLogin from "@/components/user/UserLogin.vue";

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
      ],
    },
    {
      path: "/login",
      name: "login",
      component: UserLogin,
    },
  ],
});

export default router;
