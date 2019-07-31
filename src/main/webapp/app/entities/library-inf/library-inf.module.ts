import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SchoolCardSharedModule } from 'app/shared';
import {
    LibraryInfComponent,
    LibraryInfDetailComponent,
    LibraryInfUpdateComponent,
    LibraryInfDeletePopupComponent,
    LibraryInfDeleteDialogComponent,
    libraryInfRoute,
    libraryInfPopupRoute
} from './';

const ENTITY_STATES = [...libraryInfRoute, ...libraryInfPopupRoute];

@NgModule({
    imports: [SchoolCardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LibraryInfComponent,
        LibraryInfDetailComponent,
        LibraryInfUpdateComponent,
        LibraryInfDeleteDialogComponent,
        LibraryInfDeletePopupComponent
    ],
    entryComponents: [LibraryInfComponent, LibraryInfUpdateComponent, LibraryInfDeleteDialogComponent, LibraryInfDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolCardLibraryInfModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
