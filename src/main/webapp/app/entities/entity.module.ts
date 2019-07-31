import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'student',
                loadChildren: './student/student.module#SchoolCardStudentModule'
            },
            {
                path: 'teacher',
                loadChildren: './teacher/teacher.module#SchoolCardTeacherModule'
            },
            {
                path: 'admin',
                loadChildren: './admin/vAdmin.module#SchoolCardAdminModule'
            },
            {
                path: 'card',
                loadChildren: './card/card.module#SchoolCardCardModule'
            },
            {
                path: 'fill-inf',
                loadChildren: './fill-inf/fill-inf.module#SchoolCardFillInfModule'
            },
            {
                path: 'lost-inf',
                loadChildren: './lost-inf/lost-inf.module#SchoolCardLostInfModule'
            },
            {
                path: 'press-inf',
                loadChildren: './press-inf/press-inf.module#SchoolCardPressInfModule'
            },
            {
                path: 'library-inf',
                loadChildren: './library-inf/library-inf.module#SchoolCardLibraryInfModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolCardEntityModule {}
