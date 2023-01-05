import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { MatLegacyButtonModule as MatButtonModule } from '@angular/material/legacy-button';
import { MatLegacyCardModule as MatCardModule } from '@angular/material/legacy-card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AboutComponent } from './about/about.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './layout/footer/footer.component';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { MovieCardComponent } from './movies-list/movie-card/movie-card.component';
import { MoviesListComponent } from './movies-list/movies-list.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';


@NgModule({
  declarations: [
    AppComponent,
    MoviesListComponent,
    PageNotFoundComponent,
    NavbarComponent,
    FooterComponent,
    AboutComponent,
    MovieCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    HttpClientModule,
    MatCardModule,
    MatDividerModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
